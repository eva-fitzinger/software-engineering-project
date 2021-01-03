package at.jku.softengws20.group1.maintenance.restservice;

import at.jku.softengws20.group1.maintenance.dummy.data.DummyRegularRepair;
import at.jku.softengws20.group1.maintenance.impl.Repair;
import at.jku.softengws20.group1.maintenance.impl.SchedulingSystem;
import at.jku.softengws20.group1.maintenance.impl.Vehicle;
import at.jku.softengws20.group1.maintenance.impl.VehicleCenter;
import at.jku.softengws20.group1.shared.controlsystem.Timeslot;
import at.jku.softengws20.group1.shared.maintenance.MaintenanceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(MaintenanceInterface.URL) //todo timeconstant
public class MaintenanceController implements MaintenanceInterface, ApplicationListener<ContextRefreshedEvent> {
    private SchedulingSystem schedulingSystem = new SchedulingSystem();

    @Autowired
    VehicleCenter vehicleCenter;

    @Override
    @PostMapping(MaintenanceInterface.NOTIFY_APPROVED_MAINTENANCE_URL)
    public void notifyApprovedMaintenance(@RequestBody Timeslot approvedTimeslot) {
        // schedule ok
        schedulingSystem.triggerRegularRepairAccepted(approvedTimeslot);
    }

    @Override
    @GetMapping(MaintenanceInterface.NOTIFY_MAINTENANCE_CAR_ARRIVED_URL + "/{carId}")
    public void notifyMaintenanceCarArrived(@PathVariable(value = "carId") String carId) {
        // car arrived
        System.out.println("Maintenance: car arrived");
        //todo don't always send because car might arrive at Maintenance Center
        vehicleCenter.triggerCarArrived(carId);
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        System.out.println("Maintenance is alive!");
        Thread sendCarsThread = new Thread(() -> {

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < 3; i++ ) {
                // calculate current time
                Repair repair = DummyRegularRepair.getRegularRepair();
                sendVehicledummy(repair);

                try {
                    Thread.sleep(50000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        sendCarsThread.start();
    }

//    @PostConstruct
//    public void init() {
//        Date startDate = new Date();
//        long startDateTime = startDate.getTime();
//        System.out.println("Maintenance is alive!");
//        schedulingSystem = new SchedulingSystem();
//
//        Thread regularRepairThread = new Thread(() -> { // fill up schedule with test data
//            int i = 0;
//            for (; ; ) {
//                schedulingSystem.addRegularRepair();
//                while (!SchedulingSystem.getCurrentRepairApproval().isApproved() || i < 10) {
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    i++;
//                }
//
//                //as time passes wait longer to make next regular repair schedule, so we have don't overload
//                try {
//                    Thread.sleep(1000L * i);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        });
//
//        Thread emergencyRepairThread = new Thread(() -> {
//            Date currentDate;
//            long timePassed;
//
//            try {
//                Thread.sleep(5000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            for (long i = 0; ; i++) {
//                // calculate current time
//                currentDate = new Date();
//                timePassed = (startDateTime - currentDate.getTime()) * TIME_CONSTANT;
//                currentDate = new Date(startDateTime + timePassed);
//
//                EmergencyRepair emergencyRepair = DummyEmergencyRepair.getEmergencyRepair(currentDate);
//                schedulingSystem.addEmergencyRepair(emergencyRepair);
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                sendVehicle(emergencyRepair);
//            }
//        });
//
//        Thread sendCarsThread = new Thread(() -> {
//            Date currentDate;
//            long timePassed;
//
//            for (; ; ) {
//                // calculate current time
//                currentDate = new Date();
//                timePassed = (startDateTime - currentDate.getTime()) * TIME_CONSTANT;
//                currentDate = new Date(startDateTime + timePassed);
//                List<Repair> schedule = schedulingSystem.getSchedule();
//                if (schedule.size() > 0 && schedule.stream()
//                        .map(Repair::getFrom)
//                        .collect(Collectors.toList()).contains(currentDate)) {
//                    Repair repair = schedule.get(schedulingSystem.getSchedule().indexOf(currentDate)); //TODO cannot work!
//                    sendVehicle(repair);
//
//                }
//            }
//        });
//
//        emergencyRepairThread.start();
//        regularRepairThread.start();
//        sendCarsThread.start();
//    }

    private void sendVehicle(Repair repair) {
        Thread car = new Thread(() -> {
            if (VehicleCenter.getNrVehicles() - repair.getNrVehiclesNeeded() >= 0) {
                Vehicle vehicle = vehicleCenter.sendCar(repair);
                schedulingSystem.getSchedule().remove(repair);
                while (vehicle != null && !vehicle.isArrived()) { // wait for car to arrive
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (vehicle != null && vehicle.isArrived()) {
                    vehicleCenter.returnCar(vehicle);
                }
            }
        });
        car.start();
    }

    private void sendVehicledummy(Repair repair) {
        Thread car = new Thread(() -> {
            if (VehicleCenter.getNrVehicles() - repair.getNrVehiclesNeeded() >= 0) {
                Vehicle vehicle = vehicleCenter.sendCar(repair);
                //schedulingSystem.getSchedule().remove(repair);
//                while (vehicle != null && !vehicle.isArrived()) { // wait for car to arrive
//                    try {
//                        Thread.sleep(5000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if (vehicle != null && vehicle.isArrived()) {
//                    vehicleCenter.returnCar(vehicle);
//                }
            }
        });
        car.start();
    }
}
