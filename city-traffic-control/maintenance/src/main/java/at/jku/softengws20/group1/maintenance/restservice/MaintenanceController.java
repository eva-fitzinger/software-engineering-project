package at.jku.softengws20.group1.maintenance.restservice;

import at.jku.softengws20.group1.maintenance.dummy.data.DummyEmergencyRepair;
import at.jku.softengws20.group1.maintenance.impl.EmergencyRepair;
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
import javax.swing.plaf.DimensionUIResource;
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
    public void init() {
        Date startDate = new Date();
        long startDateTime = startDate.getTime();
        System.out.println("I'm alive!");
        schedulingSystem = new SchedulingSystem();

        Thread regularRepairThread = new Thread(() -> { // fill up schedule with test data
            int i = 0;
            for (;;) {
                schedulingSystem.addRegularRepair();
                while (!SchedulingSystem.getCurrentRepairApproval().isApproved() || i < 10) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    i++;
                }

                //as time passes wait longer to make next regular repair schedule, so we have don't overload
                try {
                    Thread.sleep(1000L * i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        Thread emergencyRepairThread = new Thread(() -> {
            Date currentDate;
            long timePassed;

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (long i = 0; ; i++) {
                // calculate current time
                currentDate = new Date();
                timePassed = (startDateTime - currentDate.getTime()) * TIME_CONSTANT;
                currentDate = new Date(startDateTime + timePassed);

                EmergencyRepair emergencyRepair = DummyEmergencyRepair.getEmergencyRepair(currentDate);
                schedulingSystem.addEmergencyRepair(emergencyRepair);
                vehicleCenter.sendCar(emergencyRepair);
                try {
                    Thread.sleep((1000L * 60 * 60 * 24) / TIME_CONSTANT); //todo does this make sense?
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread sendCarsThread = new Thread(() -> {
            Date currentDate;
            long timePassed;

            for (; ; ) {
                // calculate current time
                currentDate = new Date();
                timePassed = (startDateTime - currentDate.getTime()) * TIME_CONSTANT;
                currentDate = new Date(startDateTime + timePassed);
                if (schedulingSystem.getSchedule().stream()
                        .map(Repair::getFrom)
                        .collect(Collectors.toList()).contains(currentDate)) {
                    Repair repair = schedulingSystem.getSchedule().get(schedulingSystem.getSchedule().indexOf(currentDate));
                    vehicleCenter.sendCar(repair);
                    schedulingSystem.getSchedule().remove(repair);
                }
            }
        });
        emergencyRepairThread.start();
        regularRepairThread.start();
        sendCarsThread.start();
    }
}
