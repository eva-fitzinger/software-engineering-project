package at.jku.softengws20.group1.participants.restservice;

import at.jku.softengws20.group1.shared.maintenance.MaintenanceCarDestination;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import at.jku.softengws20.group1.shared.participants.ParticipantsInterface;

import java.util.Collection;

@RestController
@RequestMapping(ParticipantsInterface.URL)
public class ParticipantsController implements ParticipantsInterface {
    @Override
    @PostMapping(ParticipantsInterface.SEND_MAINTENANCE_CAR_URL)
    public void sendMaintenanceCar(@RequestBody MaintenanceCarDestination request) {

    }

    @Override
    @PostMapping(ParticipantsInterface.NOTIFY_CARS_PASSED_URL)
    public void notifyCarsPassed(@RequestBody Collection<String> carIds) {

    }
}
