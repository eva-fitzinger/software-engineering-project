package at.jku.softengws20.group1.interfaces.controlsystem;

public interface RoadNetwork {
    Crossing[] getCrossings();
    RoadSegment[] getRoadSegments();
    Road[] getRoads();
    String getMaintenanceCenterRoadSegmentId();
}
