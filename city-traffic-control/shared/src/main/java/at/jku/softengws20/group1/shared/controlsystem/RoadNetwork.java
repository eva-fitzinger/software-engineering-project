package at.jku.softengws20.group1.shared.controlsystem;

import java.util.Collection;

public interface RoadNetwork<T0 extends Crossing, T1 extends RoadSegment, T2 extends Road> {
    Collection<T0> getCrossings();
    Collection<T1> getRoadSegments();
    Collection<T2> getRoads();
    String getMaintenanceCenterRoadSegmentId();
}
