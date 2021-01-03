package at.jku.softengws20.group1.maintenance.impl;

import at.jku.softengws20.group1.maintenance.restservice.ParticipantService_Maintenance;
import at.jku.softengws20.group1.shared.maintenance.MaintenanceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleCenter {

    @Autowired
    private CityMapService cityMapService;

    @Autowired
    private ServletContext servletContext;

    public static final int MAX_VEHICLES = 10;
    public static final int MAX_EMPLOYEES = 50;
    private static int nrVehicles = 25;
    private ParticipantService_Maintenance participantServiceMaintenance = new ParticipantService_Maintenance();
    private static List<Vehicle> vehicles = new ArrayList<>();
    private static int vehicleId;

    VehicleCenter() {
        for (int i = 0; i < 10; i++) {
            vehicles.add(new Vehicle("VH" + vehicleId));
            vehicleId++;
        }
    }

    public @Nullable Vehicle sendCar(Repair repair) {
//        int localNumVehicles = nrVehicles;
//        localNumVehicles -= repair.getNrVehiclesNeeded();
//        if (localNumVehicles >= 0) { // todo should also send more cars
//            at.jku.softengws20.group1.shared.impl.model.CarPath destination = new at.jku.softengws20.group1.shared.impl.model.CarPath(); //TODO
//            participantServiceMaintenance.sendCar(destination);
//            nrVehicles = localNumVehicles;
//            return vehicles.stream().filter(Vehicle::isAvailable).findFirst().orElse(null);
//        }
        return null;
    }

    public static int getNrVehicles() {
        return nrVehicles;
    }

    public void triggerCarArrived(String id) {
//     TODO   vehicles.stream().filter(x -> x.destination.equals(destination)).findFirst().ifPresent(vehicle -> vehicle.setArrived(true));
    }

    public void returnCar(Vehicle vehicle) {
        nrVehicles++;
        participantServiceMaintenance.sendCar(new at.jku.softengws20.group1.shared.impl.model.CarPath(vehicle.destination.getStartRoadSegmentId(), 0, cityMapService.getRoadNetwork().getMaintenanceCenterRoadSegmentId(), 0, getUriString(vehicle)));
    }

    private String getUriString(Vehicle vehicle) {
        return servletContext.getContextPath()  + MaintenanceInterface.URL + "/" + MaintenanceInterface.NOTIFY_APPROVED_MAINTENANCE_URL
                + "/" + vehicle.getId();
    }
}
