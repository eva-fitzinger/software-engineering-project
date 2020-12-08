package at.jku.softengws20.group1.shared.impl.model;

import java.util.Collection;

public class MaintenanceRequest<T0 extends Timeslot>
        implements at.jku.softengws20.group1.shared.controlsystem.MaintenanceRequest<T0> {

    private String requestType;
    private String roadSegmentId;
    private Collection<T0> timeslots;

    public MaintenanceRequest() {}

    public MaintenanceRequest(String requestType, String roadSegmentId, Collection<T0> timeslots) {
        this.requestType = requestType;
        this.roadSegmentId = roadSegmentId;
        this.timeslots = timeslots;
    }

    @Override
    public String getRequestType() {
        return requestType;
    }

    @Override
    public String getRoadSegmentId() {
        return roadSegmentId;
    }

    @Override
    public Collection<T0> getTimeSlots() {
        return timeslots;
    }
}
