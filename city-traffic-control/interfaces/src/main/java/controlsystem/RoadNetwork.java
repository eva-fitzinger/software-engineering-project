package controlsystem;

public interface RoadNetwork {
    Crossing[] getCrossings();
    RoadSegment[] getRoadSegments();
    Road[] getRoads();
    String getMaintenanceCenterRoadSegmentId();
}
