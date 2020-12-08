package at.jku.softengws20.group1.shared.impl.service;

import at.jku.softengws20.group1.shared.maintenance.MaintenanceCarDestination;
import at.jku.softengws20.group1.shared.participants.ParticipantsInterface;

public abstract class ParticipantsRestService extends BaseService implements ParticipantsInterface {
    protected ParticipantsRestService() {
        super(ParticipantsInterface.URL);
    }

    @Override
    public void sendMaintenanceCar(MaintenanceCarDestination request) {
        post(ParticipantsInterface.SEND_MAINTENANCE_CAR_URL, request);
    }

    @Override
    public void notifyCarsPassed(String[] carIds) {
        post(ParticipantsInterface.NOTIFY_CARS_PASSED_URL, carIds);
    }
}
