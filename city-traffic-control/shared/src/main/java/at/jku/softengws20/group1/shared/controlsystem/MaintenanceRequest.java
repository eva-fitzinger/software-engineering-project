package at.jku.softengws20.group1.shared.controlsystem;

public interface MaintenanceRequest {
    String getRequestId();
    String getRequestType();
    String getRoadSegmentId();
    Timeslot[] getTimeSlots();
}
