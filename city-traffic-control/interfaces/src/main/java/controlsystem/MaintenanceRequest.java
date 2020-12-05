package controlsystem;

public interface MaintenanceRequest {
    String getRequestType();
    String getRoadSegmentId();
    Timeslot[] getTimeSlots();
}
