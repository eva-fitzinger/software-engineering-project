package at.jku.softengws20.group1.maintenance.restservice;

import at.jku.softengws20.group1.maintenance.impl.Repair;
import at.jku.softengws20.group1.maintenance.impl.SchedulingSystem;
import at.jku.softengws20.group1.maintenance.impl.VehicleCenter;
import at.jku.softengws20.group1.shared.controlsystem.Timeslot;
import at.jku.softengws20.group1.shared.maintenance.MaintenanceCarDestination;
import at.jku.softengws20.group1.shared.maintenance.MaintenanceInterface;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.stream.Collectors;


@RestController
@RequestMapping(MaintenanceInterface.URL)
public class MaintenanceController implements MaintenanceInterface {
    private final int TIME_CONSTANT = 1;
    private SchedulingSystem schedulingSystem;
    private VehicleCenter vehicleCenter;

    @Override
    @PostMapping(MaintenanceInterface.NOTIFY_APPROVED_MAINTENANCE_URL)
    public void notifyApprovedMaintenance(@RequestBody Timeslot approvedTimeslot) {
        // schedule ok
        schedulingSystem.triggerRegularRepairAccepted(approvedTimeslot);
    }

    @Override
    @PostMapping(MaintenanceInterface.NOTIFY_MAINTENANCE_CAR_ARRIVED_URL)
    public void notifyMaintenanceCarArrived(@RequestBody MaintenanceCarDestination destination) {
        // car arrived
    }

    @PostConstruct
    public void init(){
        Date startDate = new Date();
        long startDateTime = startDate.getTime();
        System.out.println("I'm alive!");
        Thread thread = new Thread(() -> {

            Date currentDate;
            long timePassed;

            try {
                Thread.sleep(200);
                schedulingSystem = new SchedulingSystem(2020,12,16, 20);
                schedulingSystem.printSchedule();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // start main
            for(long i = 0;;i++){
                // calculate current time
                currentDate = new Date();
                timePassed = (startDateTime - currentDate.getTime()) * TIME_CONSTANT;
                currentDate = new Date(startDateTime + timePassed);

                if(i % 3000 == 0) schedulingSystem.addEmergencyRepair();
                if(i % 45000 == 0) {
                    schedulingSystem.addRegularRepair();
                    try {
                        Thread.sleep(300); //TODO find out how long a response from Control usually takes
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if(schedulingSystem.getSchedule().stream()
                        .map(Repair::getFrom)
                        .collect(Collectors.toList()).contains(currentDate)){
                    Repair repair = schedulingSystem.getSchedule().get(schedulingSystem.getSchedule().indexOf(currentDate));
                    vehicleCenter.sendCar(repair);
                    schedulingSystem.getSchedule().remove(repair);
                }

            }
        });
        thread.start();
    }
}
