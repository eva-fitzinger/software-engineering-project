package at.jku.softengws20.group1.shared.controlsystem;

import java.util.Collection;

public interface MaintenanceRequest<T extends Timeslot> {
    String getRequestType();
    String getRoadSegmentId();
    Collection<T> getTimeSlots();
}
