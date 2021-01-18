package at.jku.softengws20.group1.shared.controlsystem;

public interface ControlSystemInterface<T extends MaintenanceRequest> {
    String URL = "/controlsystem";

    String GET_ROAD_NETWORK_URL = "roadNetwork";

    /**
     * This method provides a valid and in itself consistent RoadNetwork.
     * @return the roadNetwork that all subsystems fetch at startup
     */
    RoadNetwork getRoadNetwork();

    String GET_STATUS_URL = "status";

    /**
     * Provides a RoadSegmentStatus for every RoadSegment that exists in the RoadNetwork
     * which can be retrieved with getRoadNetwork()
     * @return an array of RoadSegmentStatus of length N, N being the number of RoadSegments in the RoadNetwork.
     */
    RoadSegmentStatus[] getStatus();

    String REQUEST_ROAD_CLOSING_URL = "requestRoadBlocking";

    /**
     * Submit a MaintenanceRequest for review or direct action (for emergency requests)
     * @param request A MaintenanceRequest with an id != null and a valid RoadSegmentId
     */
    void requestRoadClosing(T request);

    String SET_ROAD_AVAILABLE_URL = "setRoadAvailable";

    /**
     * Notifies the ControlCenter, that a previously closed road (by MaintenanceRequest or manual closing) is reopened.
     * @param roadSegmentId A valid id of an existing RoadSegment in the RoadNetwork
     */
    void setRoadAvailable(String roadSegmentId);
}