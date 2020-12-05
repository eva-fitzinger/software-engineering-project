package restservice;

import controlsystem.Timeslot;
import maintenance.MaintenanceCarDestination;
import maintenance.MaintenanceInterface;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/maintenance")
public class MaintenanceController implements MaintenanceInterface {
    @Override
    @PostMapping("notifyApprovedMaintenance")
    public void notifyApprovedMaintenance(@RequestBody Timeslot approvedTimeslot) {

    }

    @Override
    @PostMapping("notifyMaintenanceCarArrived")
    public void notifyMaintenanceCarArrived(@RequestBody MaintenanceCarDestination destination) {

    }
}
