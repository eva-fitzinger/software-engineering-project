package at.jku.softengws20.group1.controlsystem.gui.model;

import at.jku.softengws20.group1.controlsystem.gui.ControlSystemService;
import at.jku.softengws20.group1.shared.impl.model.Crossing;
import at.jku.softengws20.group1.shared.impl.model.RoadSegmentStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class LocalDataRepository {

    private HashMap<String, Road> roadsById = new HashMap<>();
    private HashMap<String, Crossing> crossingsById = new HashMap<>();
    private HashMap<String, RoadSegment> roadSegmentsById = new HashMap<>();

    private HashMap<String, ObservableTrafficLoad> trafficInformation = new HashMap<>();
    private HashMap<Crossing, Collection<ObservableTrafficLoad>> trafficInfoByCrossing = new HashMap<>();

    private HashMap<String, RoadSegmentStatus> statusByRoadSegmentId = new HashMap<>();

    public LocalDataRepository() {
        loadRoadNetwork();
    }

    private void loadRoadNetwork() {
        ControlSystemService controlSystemService = new ControlSystemService();
        RoadNetwork roadNetwork = controlSystemService.getRoadNetwork();

        for(var road : roadNetwork.getRoads()) {
            roadsById.put(road.getId(), road);
        }
        for (Crossing c : roadNetwork.getCrossings()) {
            crossingsById.put(c.getId(), c);
        }
        for (var rs: roadNetwork.getRoadSegments()) {
            roadSegmentsById.put(rs.getId(), rs);
            roadsById.get(rs.getRoadId()).getRoadSegments().add(rs);
            rs.setRoad(roadsById.get(rs.getRoadId()));
            rs.setCrossingA(crossingsById.get(rs.getCrossingAId()));
            rs.setCrossingB(crossingsById.get(rs.getCrossingBId()));
        }
    }

    public void updateTrafficInformation(RoadSegmentStatus[] statusArray) {
        for(var trafficStatus : statusArray) {
            var key = trafficStatus.getRoadSegmentId();
            var ti = trafficInformation.getOrDefault(key, null);
            if(ti == null) {
                var rs = getRoadSegmentById(trafficStatus.getRoadSegmentId());
                ti = new ObservableTrafficLoad(rs, trafficStatus);
                trafficInformation.put(key, ti);
                var infoList = trafficInfoByCrossing.getOrDefault(rs.getCrossingB(), null);
                if (infoList == null) {
                    infoList = new ArrayList<>();
                    trafficInfoByCrossing.put(rs.getCrossingB(), infoList);
                }
                infoList.add(ti);
            } else {
                ti.update(trafficStatus);
            }
        }
    }


    RoadSegment getRoadSegmentById(String roadSegmentId) {
        return roadSegmentsById.getOrDefault(roadSegmentId, null);
    }

    public Crossing getCrossingById(String crossingId) {
        return crossingsById.getOrDefault(crossingId, null);
    }

    public Collection<Crossing> getCrossings() {
        return crossingsById.values();
    }

    public Collection<Road> getRoads() {
        return roadsById.values();
    }

    public Collection<ObservableTrafficLoad> getTrafficInformation(Crossing crossing) {
        return trafficInfoByCrossing.getOrDefault(crossing, new ArrayList<>());
    }

    public RoadSegmentStatus getStatusByRoadSegmentId(String roadSegmentId) {
        return statusByRoadSegmentId.getOrDefault(roadSegmentId, null);
    }
}
