package at.jku.softengws20.group1.maintenance.restservice;

import at.jku.softengws20.group1.maintenance.dummy.data.DummyEmergencyRepair;
import at.jku.softengws20.group1.maintenance.impl.EmergencyRepair;
import at.jku.softengws20.group1.maintenance.impl.Repair;
import at.jku.softengws20.group1.maintenance.impl.SchedulingSystem;
import at.jku.softengws20.group1.maintenance.impl.VehicleCenter;
import at.jku.softengws20.group1.shared.impl.model.MaintenanceRequest;
import at.jku.softengws20.group1.shared.impl.model.Timeslot;
import at.jku.softengws20.group1.shared.maintenance.MaintenanceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping(MaintenanceInterface.URL) //todo timeconstant
public class MaintenanceController implements MaintenanceInterface<MaintenanceRequest<Timeslot>>, ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    VehicleCenter vehicleCenter;

    @Autowired
    SchedulingSystem schedulingSystem;

    @Override
    @PostMapping(MaintenanceInterface.NOTIFY_APPROVED_MAINTENANCE_URL)
    public void notifyApprovedMaintenance(@RequestBody MaintenanceRequest<Timeslot> approvedMaintenanceRequest) {
        // schedule ok
        schedulingSystem.triggerRegularRepairAccepted(approvedMaintenanceRequest);
    }

    @Override
    @GetMapping(MaintenanceInterface.NOTIFY_MAINTENANCE_CAR_ARRIVED_URL + "/{carId}")
    public void notifyMaintenanceCarArrived(@PathVariable(value = "carId") String carId) {
        // car arrived
        vehicleCenter.triggerCarArrived(carId);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        System.out.println("Maintenance is alive!");
        //----- send Cars --------------------------------------------------------------
        Thread sendCarsThread = new Thread(() -> {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; ; i++) {
                final Date currentDate = new Date();
                List<Repair> schedule = schedulingSystem.getSchedule();
                schedule.stream()
                        .filter(x -> x.getFrom().before(currentDate))
                        .findAny().ifPresent(this::sendVehicle);

                try {
                    Thread.sleep(50000L * i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        //----- regularRepair --------------------------------------------------------------
        Thread regularRepairThread = new Thread(() -> {// fill up schedule with test data
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int i = 0;
            for (; ; i++) {
                schedulingSystem.addRegularRepair();
                //as time passes wait longer to make next regular repair schedule,
                // so we don't have an overload
                try {
                    Thread.sleep(1000L * i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
        //---------------------------Emergency Repair------------------------------------------------
        Thread emergencyRepairThread = new Thread(() -> {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (long i = 0; ; i++) {

                EmergencyRepair emergencyRepair = DummyEmergencyRepair.getEmergencyRepair(new Date());
                schedulingSystem.addEmergencyRepair(emergencyRepair);
                try {
                    Thread.sleep(30000 * i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sendVehicle(emergencyRepair);
            }
        });

        sendCarsThread.start();
        regularRepairThread.start();
        emergencyRepairThread.start();
    }

    private void sendVehicle(Repair repair) {
        Thread car = new Thread(() -> {
            if (VehicleCenter.getNrVehicles() - repair.getNrVehiclesNeeded() >= 0) {
                vehicleCenter.sendCar(repair);
                schedulingSystem.getSchedule().remove(repair);
            }
        });
        car.start();
    }
}
