package controlsystem;

public interface ControlSystemInterface {
    RoadNetwork getRoadNetwork();
    RoadSegmentStatus[] getStatus();
    void requestRoadClosing(MaintenanceRequest request);
    void setRoadAvailable(String roadSegmentId);
}