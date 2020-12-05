package at.jku.softengws20.group1.interfaces.impl;

import at.jku.softengws20.group1.interfaces.controlsystem.ControlSystemInterface;
import at.jku.softengws20.group1.interfaces.controlsystem.MaintenanceRequest;
import at.jku.softengws20.group1.interfaces.controlsystem.RoadNetwork;
import at.jku.softengws20.group1.interfaces.controlsystem.RoadSegmentStatus;

public abstract class ControlSystemRestService<T0 extends RoadNetwork, T1 extends RoadSegmentStatus>
        extends BaseService implements ControlSystemInterface {

    private Class<T0> roadNetworkClass;
    private Class<T1[]> roadSegmentStatusClass;

    public ControlSystemRestService(Class<T0> roadNetworkClass, Class<T1[]> roadSegmentStatusClass) {
        super(ControlSystemInterface.URL);
        this.roadNetworkClass = roadNetworkClass;
        this.roadSegmentStatusClass = roadSegmentStatusClass;
    }

    @Override
    public T0 getRoadNetwork() {
        return successBodyOrNull(ControlSystemInterface.GET_ROAD_NETWORK_URL, roadNetworkClass);
    }

    @Override
    public T1[] getStatus() {
        return successBodyOrNull(ControlSystemInterface.GET_STATUS_URL, roadSegmentStatusClass);
    }

    @Override
    public void requestRoadClosing(MaintenanceRequest request) {
        post(ControlSystemInterface.REQUEST_ROAD_CLOSING_URL, request);
    }

    @Override
    public void setRoadAvailable(String roadSegmentId) {
        post(ControlSystemInterface.SET_ROAD_AVAILABLE_URL, roadSegmentId);
    }
}
