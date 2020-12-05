package participants;

import maintenance.MaintenanceCarDestination;

public interface ParticipantsInterface {
    void sendMaintenanceCar(MaintenanceCarDestination request);
    void notifyCarsPassed(String[] carIds);
}
