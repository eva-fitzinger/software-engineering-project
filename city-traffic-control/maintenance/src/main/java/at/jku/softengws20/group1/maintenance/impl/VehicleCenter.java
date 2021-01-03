package at.jku.softengws20.group1.maintenance.impl;

import at.jku.softengws20.group1.maintenance.restservice.ParticipantService_Maintenance;
import at.jku.softengws20.group1.shared.maintenance.MaintenanceCarDestination;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public class VehicleCenter {

    public static final int MAX_VEHICLES = 10;
    public static final int MAX_EMPLOYEES = 50;
    private static final String MAINTENANCE_STATION = "MAINTENANCE STATION";
    private static int nrVehicles = 25;
    private ParticipantService_Maintenance participantServiceMaintenance = new ParticipantService_Maintenance();
    private static List<Vehicle> vehicles;
    private static int vehicleId;

    VehicleCenter() {
        for (int i = 0; i < 10; i++) {
            vehicles.add(new Vehicle("VH" + vehicleId));
            vehicleId++;
        }
    }

    public @Nullable
    Vehicle sendCar(Repair repair) {
        int localNumVehicles = nrVehicles;
        localNumVehicles -= repair.getNrVehiclesNeeded();
        if (localNumVehicles >= 0) { // todo should also send more cars
            MaintenanceCarDestination destination = new at.jku.softengws20.group1.shared.impl.model.MaintenanceCarDestination(repair.getLocation(), vehicles.get(0).getId());
            participantServiceMaintenance.sendMaintenanceCar(destination);
            nrVehicles = localNumVehicles;
            return vehicles.stream().filter(Vehicle::isAvailable).findFirst().orElse(null);
        }
        return null;
    }

    public static int getNrVehicles() {
        return nrVehicles;
    }

    public void triggerCarArrived(MaintenanceCarDestination destination) {
        vehicles.stream().filter(x -> x.destination.equals(destination)).findFirst().ifPresent(vehicle -> vehicle.setArrived(true));
    }

    public void returnCar(Vehicle vehicle) {
        nrVehicles++;
        participantServiceMaintenance.sendMaintenanceCar(new at.jku.softengws20.group1.shared.impl.model.MaintenanceCarDestination(MAINTENANCE_STATION, vehicle.getId()));
    }
}
