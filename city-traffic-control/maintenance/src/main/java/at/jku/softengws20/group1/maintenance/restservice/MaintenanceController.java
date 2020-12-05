package at.jku.softengws20.group1.maintenance.restservice;

import at.jku.softengws20.group1.interfaces.controlsystem.Timeslot;
import at.jku.softengws20.group1.interfaces.maintenance.MaintenanceCarDestination;
import at.jku.softengws20.group1.interfaces.maintenance.MaintenanceInterface;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(MaintenanceInterface.URL)
public class MaintenanceController implements MaintenanceInterface {
    @Override
    @PostMapping(MaintenanceInterface.NOTIFY_APPROVED_MAINTENANCE_URL)
    public void notifyApprovedMaintenance(@RequestBody Timeslot approvedTimeslot) {

    }

    @Override
    @PostMapping(MaintenanceInterface.NOTIFY_MAINTENANCE_CAR_ARRIVED_URL)
    public void notifyMaintenanceCarArrived(@RequestBody MaintenanceCarDestination destination) {

    }
}
