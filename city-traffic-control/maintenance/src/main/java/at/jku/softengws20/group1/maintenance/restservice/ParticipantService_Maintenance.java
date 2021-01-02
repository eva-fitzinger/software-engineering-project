package at.jku.softengws20.group1.maintenance.restservice;

import at.jku.softengws20.group1.shared.impl.service.ParticipantsRestService;
import at.jku.softengws20.group1.shared.maintenance.MaintenanceCarDestination;
import at.jku.softengws20.group1.shared.participants.ParticipantsInterface;
import org.springframework.stereotype.Service;

@Service
public class ParticipantService_Maintenance extends ParticipantsRestService {

    @Override
    public void sendMaintenanceCar(MaintenanceCarDestination request) {
        post(ParticipantsInterface.SEND_MAINTENANCE_CAR_URL, request);
    }
}
