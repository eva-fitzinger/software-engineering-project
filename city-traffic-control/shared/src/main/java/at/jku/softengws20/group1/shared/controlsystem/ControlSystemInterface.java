package at.jku.softengws20.group1.shared.controlsystem;

public interface ControlSystemInterface {
    String URL = "/controlsystem";

    String GET_ROAD_NETWORK_URL = "roadNetwork";
    RoadNetwork getRoadNetwork();

    String GET_STATUS_URL = "status";
    RoadSegmentStatus[] getStatus();

    String REQUEST_ROAD_CLOSING_URL = "requestRoadBlocking";
    void requestRoadClosing(MaintenanceRequest request);

    String SET_ROAD_AVAILABLE_URL = "setRoadAvailable";
    void setRoadAvailable(String roadSegmentId);
}