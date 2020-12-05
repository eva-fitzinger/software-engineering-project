package at.jku.softengws20.group1.interfaces.participants;

import at.jku.softengws20.group1.interfaces.maintenance.MaintenanceCarDestination;

public interface ParticipantsInterface {
    void sendMaintenanceCar(MaintenanceCarDestination request);
    void notifyCarsPassed(String[] carIds);
}
