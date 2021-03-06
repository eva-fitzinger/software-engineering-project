package at.jku.softengws20.group1.shared.impl.model;

public class RoadNetwork<T0 extends Crossing, T1 extends RoadSegment, T2 extends Road>
        implements at.jku.softengws20.group1.shared.controlsystem.RoadNetwork {

    private T0[] crossings;
    private T1[] roadSegments;
    private T2[] roads;
    private String maintenanceCenterRoadSegmentId;

    public RoadNetwork() {}

    public RoadNetwork(T0[] crossings, T1[] roadSegments, T2[] roads, String maintenanceCenterRoadSegmentId) {
        this.crossings = crossings;
        this.roadSegments = roadSegments;
        this.roads = roads;
        this.maintenanceCenterRoadSegmentId = maintenanceCenterRoadSegmentId;
    }

    @Override
    public T0[] getCrossings() {
        return crossings;
    }

    @Override
    public T1[] getRoadSegments() {
        return roadSegments;
    }

    @Override
    public T2[] getRoads() {
        return roads;
    }

    @Override
    public String getMaintenanceCenterRoadSegmentId() {
        return maintenanceCenterRoadSegmentId;
    }
}
