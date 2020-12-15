package at.jku.softengws20.group1.controlsystem.gui.osm_import;

import at.jku.softengws20.group1.shared.impl.model.Position;
import at.jku.softengws20.group1.shared.impl.model.RoadType;

import java.util.*;

class ImportedRoadNetwork {

    private final double LANE_DIST = 0.005;

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

    Map<String, ImportedCrossing> getCrossings() {
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
            if (!way.isOneWay()) {
                r.getRoadSegments().add(createDirectedLane(currentSegment, way));
            }
            currentSegment = createRoadData(r, way);
            currentSegment.setCrossingA(getOrCreateCrossing(node, proj));
        }
    }

    private ImportedRoadSegment createDirectedLane(ImportedRoadSegment s1, OSMWay way) {
        ImportedRoadSegment s2 = createRoadData(s1.getRoad(), way);

        s2.setCrossingA(s1.getCrossingB());
        s2.setCrossingB(s1.getCrossingA());

        List<Position> path = new ArrayList<>(s1.getPath());
        Collections.reverse(path);

        s1.setPath(movePathToRight(s1.getPath()));
        s2.setPath(movePathToRight(path));
        return s2;
    }

    private List<Position> movePathToRight(Collection<Position> path) {
        var newPath = new ArrayList<Position>();
        Position last = null;
        double x = 0;
        double y = 0;
        for (var p : path) {
            if (last == null) {
                last = p;
                continue;
            }
            double dx = p.getX() - last.getX();
            double dy = p.getY() - last.getY();
            y = dx;
            x = -dy;
            double l = Math.sqrt(x * x + y * y);
            y *= LANE_DIST / l;
            x *= LANE_DIST / l;
            newPath.add(new Position(last.getX() + x, last.getY() + y));
            last = p;
        }
        if (last != null) {
            newPath.add(new Position(last.getX() + x, last.getY() + y));
        }
        return newPath;
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
