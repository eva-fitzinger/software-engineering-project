package at.jku.softengws20.group1.interfaces.participants;

import at.jku.softengws20.group1.interfaces.maintenance.MaintenanceCarDestination;

public interface ParticipantsInterface {
    String URL = "/participants";

    String SEND_MAINTENANCE_CAR_URL = "sendMaintenanceCar";
    void sendMaintenanceCar(MaintenanceCarDestination request);

    String NOTIFY_CARS_PASSED_URL = "notifyCarsPassed";
    void notifyCarsPassed(String[] carIds);
}
