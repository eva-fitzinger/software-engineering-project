package at.jku.softengws20.group1.participants.restservice;

import at.jku.softengws20.group1.interfaces.maintenance.MaintenanceCarDestination;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import at.jku.softengws20.group1.interfaces.participants.ParticipantsInterface;

@RestController
@RequestMapping("/participants")
public class ParticipantsController implements ParticipantsInterface {
    @Override
    @PostMapping("sendMaintenanceCar")
    public void sendMaintenanceCar(@RequestBody MaintenanceCarDestination request) {

    }

    @Override
    @PostMapping("notifyCarsPassed")
    public void notifyCarsPassed(@RequestBody String[] carIds) {

    }
}
