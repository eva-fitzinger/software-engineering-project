package at.jku.softengws20.group1.interfaces.controlsystem;

public interface MaintenanceRequest {
    String getRequestType();
    String getRoadSegmentId();
    Timeslot[] getTimeSlots();
}
