package at.jku.softengws20.group1.controlsystem.gui.osm_import;

import at.jku.softengws20.group1.shared.impl.model.RoadType;

import java.util.HashMap;
import java.util.Map;

class ImportedRoadNetwork {

    private Map<String, ImportedRoad> roads = new HashMap<>();
    private Map<String, ImportedCrossing> crossings = new HashMap<>();

    static ImportedRoadNetwork fromOSMModel(OSMStreetNetwork osm, LatLonProjector proj) {
        osm.getNodes().values().forEach(n -> proj.addPos(n.getLat(), n.getLon()));

        ImportedRoadNetwork roadNetwork = new ImportedRoadNetwork();
        Map<String, ImportedRoad> roadsByName = new HashMap<>();
        for (OSMWay way : osm.getWays().values()) {
            String name = way.getTags().get("name");
            ImportedRoad r = roadsByName.getOrDefault(name, null);
            if (r == null) {
                r = new ImportedRoad();
                r.setId(way.getId());
                r.setName(name);
                r.setNumber(way.getRoadRef());

                roadNetwork.getRoads().put(r.getId(), r);
                if (name != null) {
                    roadsByName.put(name, r);
                }
            }
            roadNetwork.createRoadSegments(r, way, proj);
        }
        return roadNetwork;
    }

    Map<String, ImportedRoad> getRoads() {
        return roads;
    }

    public Map<String, ImportedCrossing> getCrossings() {
        return crossings;
    }

    private void createRoadSegments(ImportedRoad r, OSMWay way, LatLonProjector proj) {
        ImportedRoadSegment currentSegment = null;
        for (OSMNode node : way.getNodes()) {
            if (currentSegment == null) {
                currentSegment = createRoadData(r, way);
                if (node.isCrossing()) {
                    currentSegment.setCrossingA(getOrCreateCrossing(node, proj));
                } else {
                    currentSegment.getPath().add(proj.project(node.getLat(), node.getLon()));
                }
                continue;
            }
            if (!node.isCrossing()) {
                currentSegment.getPath().add(proj.project(node.getLat(), node.getLon()));
                continue;
            }
            currentSegment.setCrossingB(getOrCreateCrossing(node, proj));
            r.getRoadSegments().add(currentSegment);
            currentSegment = createRoadData(r, way);
            currentSegment.setCrossingA(getOrCreateCrossing(node, proj));
        }
    }

    private ImportedRoadSegment createRoadData(ImportedRoad road, OSMWay way) {
        ImportedRoadSegment segment = new ImportedRoadSegment();
        segment.setId(road.getId() + "_" + road.getRoadSegments().size());
        segment.setRoadType(RoadType.fromOSMName(way.getRoadType()));
        segment.setSpeedLimit(way.getSpeedLimit());
        segment.setRoad(road);
        return segment;
    }

    private ImportedCrossing getOrCreateCrossing(OSMNode node, LatLonProjector proj) {
        ImportedCrossing crossing = crossings.getOrDefault(node.getId(), null);
        if (crossing == null) {
            crossing = new ImportedCrossing();
            crossing.setId(node.getId());
            crossing.setPosition(proj.project(node.getLat(), node.getLon()));
            crossings.put(crossing.getId(), crossing);
        }
        return crossing;
    }
}
