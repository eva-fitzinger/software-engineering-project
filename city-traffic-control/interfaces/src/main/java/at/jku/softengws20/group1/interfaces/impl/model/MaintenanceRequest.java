package at.jku.softengws20.group1.interfaces.impl.model;

public class MaintenanceRequest<T0 extends Timeslot> implements at.jku.softengws20.group1.interfaces.controlsystem.MaintenanceRequest {

    private String requestType;
    private String roadSegmentId;
    private T0[] timeslots;

    public MaintenanceRequest() {}

    public MaintenanceRequest(String requestType, String roadSegmentId, T0[] timeslots) {
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
    public T0[] getTimeSlots() {
        return timeslots;
    }
}
