package at.jku.softengws20.group1.shared.impl.model;

public class MaintenanceRequest<T0 extends Timeslot> implements at.jku.softengws20.group1.shared.controlsystem.MaintenanceRequest {

    private String requestId;
    private String requestType;
    private String roadSegmentId;
    private T0[] timeSlots;

    public MaintenanceRequest() {}

    public MaintenanceRequest(String requestId, String requestType, String roadSegmentId, T0[] timeslots) {
        this.requestId = requestId;
        this.requestType = requestType;
        this.roadSegmentId = roadSegmentId;
        this.timeSlots = timeslots;
    }

    @Override
    public String getRequestId() { return requestId; }

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
        return timeSlots;
    }
}
