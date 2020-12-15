package at.jku.softengws20.group1.controlsystem.gui.model;

import at.jku.softengws20.group1.controlsystem.gui.ControlSystemService;
import at.jku.softengws20.group1.shared.detection.TrafficLoad;
import at.jku.softengws20.group1.shared.impl.model.Crossing;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class LocalDataRepository {

    private HashMap<String, Road> roadsById = new HashMap<>();
    private HashMap<String, Crossing> crossingsById = new HashMap<>();
    private HashMap<String, RoadSegment> roadSegmentsById = new HashMap<>();

    private HashMap<TrafficInformationId, ObservableTrafficLoad> trafficInformation = new HashMap<>();
    private HashMap<Crossing, Collection<ObservableTrafficLoad>> trafficInfoByCrossing = new HashMap<>();

    private Collection<DummyScenario> activeScenarios = new ArrayList<>();

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

    public void updateTrafficInformation(TrafficLoad[] trafficLoadArray) {
        for(var trafficLoad : trafficLoadArray) {
            var key = new TrafficInformationId(trafficLoad.getCrossingId(), trafficLoad.getRoadSegmentId());
            var ti = trafficInformation.getOrDefault(key, null);
            if(ti == null) {
                ti = new ObservableTrafficLoad(getRoadSegmentById(trafficLoad.getRoadSegmentId()), getCrossingById(trafficLoad.getCrossingId()), trafficLoad.getCarsWaiting());
                trafficInformation.put(key, ti);
                var infoList = trafficInfoByCrossing.getOrDefault(ti.getCrossing(), null);
                if (infoList == null) {
                    infoList = new ArrayList<>();
                    trafficInfoByCrossing.put(ti.getCrossing(), infoList);
                }
                infoList.add(ti);
            }
        }
    }


    public RoadSegment getRoadSegmentById(String roadSegmentId) {
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
}
