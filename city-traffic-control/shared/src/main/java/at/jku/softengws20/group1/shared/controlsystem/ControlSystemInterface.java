package at.jku.softengws20.group1.shared.controlsystem;

import java.util.Collection;

public interface ControlSystemInterface<T0 extends RoadNetwork, T1 extends RoadSegmentStatus> {
    String URL = "/controlsystem";

    String GET_ROAD_NETWORK_URL = "roadNetwork";
    T0 getRoadNetwork();

    String GET_STATUS_URL = "status";
    Collection<T1> getStatus();

    String REQUEST_ROAD_CLOSING_URL = "requestRoadBlocking";
    void requestRoadClosing(MaintenanceRequest request);

    String SET_ROAD_AVAILABLE_URL = "setRoadAvailable";
    void setRoadAvailable(String roadSegmentId);
}