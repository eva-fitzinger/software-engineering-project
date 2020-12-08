package at.jku.softengws20.group1.shared.impl.model;

import java.util.Collection;

public class RoadNetwork<T0 extends Crossing, T1 extends RoadSegment, T2 extends Road>
        implements at.jku.softengws20.group1.shared.controlsystem.RoadNetwork {

    private Collection<T0> crossings;
    private Collection<T1> roadSegments;
    private Collection<T2> roads;
    private String maintenanceCenterRoadSegmentId;

    public RoadNetwork() {}

    public RoadNetwork(Collection<T0> crossings, Collection<T1> roadSegments, Collection<T2> roads, String maintenanceCenterRoadSegmentId) {
        this.crossings = crossings;
        this.roadSegments = roadSegments;
        this.roads = roads;
        this.maintenanceCenterRoadSegmentId = maintenanceCenterRoadSegmentId;
    }

    @Override
    public Collection<T0> getCrossings() {
        return crossings;
    }

    @Override
    public Collection<T1> getRoadSegments() {
        return roadSegments;
    }

    @Override
    public Collection<T2> getRoads() {
        return roads;
    }

    @Override
    public String getMaintenanceCenterRoadSegmentId() {
        return maintenanceCenterRoadSegmentId;
    }
}
