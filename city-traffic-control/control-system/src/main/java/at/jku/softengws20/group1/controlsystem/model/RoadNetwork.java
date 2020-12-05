package at.jku.softengws20.group1.controlsystem.model;

import at.jku.softengws20.group1.interfaces.controlsystem.Crossing;
import at.jku.softengws20.group1.interfaces.controlsystem.RoadSegment;

public class RoadNetwork implements at.jku.softengws20.group1.interfaces.controlsystem.RoadNetwork {

    private Crossing[] crossings;
    private RoadSegment[] roadSegments;
    private Road[] roads;
    private String maintenanceCenterRoadSegmentId;

    public RoadNetwork(Crossing[] crossings, RoadSegment[] roadSegments, Road[] roads, String maintenanceCenterRoadSegmentId) {
        this.crossings = crossings;
        this.roadSegments = roadSegments;
        this.roads = roads;
        this.maintenanceCenterRoadSegmentId = maintenanceCenterRoadSegmentId;
    }

    @Override
    public Crossing[] getCrossings() {
        return crossings;
    }

    @Override
    public RoadSegment[] getRoadSegments() {
        return roadSegments;
    }

    @Override
    public Road[] getRoads() {
        return roads;
    }

    @Override
    public String getMaintenanceCenterRoadSegmentId() {
        return maintenanceCenterRoadSegmentId;
    }
}
