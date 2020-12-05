package at.jku.softengws20.group1.interfaces.controlsystem;

public interface ControlSystemInterface {
    RoadNetwork getRoadNetwork();
    RoadSegmentStatus[] getStatus();
    void requestRoadClosing(MaintenanceRequest request);
    void setRoadAvailable(String roadSegmentId);
}