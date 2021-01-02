package at.jku.softengws20.group1.maintenance.impl;

import at.jku.softengws20.group1.maintenance.restservice.ParticipantService_Maintenance;
import at.jku.softengws20.group1.shared.maintenance.MaintenanceCarDestination;

import java.util.List;

public class VehicleCenter {

    public static final int MAX_VEHICLES = 10;
    public static final int MAX_EMPLOYEES = 50;
    private ParticipantService_Maintenance participantServiceMaintenance = new ParticipantService_Maintenance();
    List<Vehicle> vehicles;

    public void sendCar(Repair repair) {
        MaintenanceCarDestination destination = new at.jku.softengws20.group1.shared.impl.model.MaintenanceCarDestination(repair.getLocation(), vehicles.get(0).getId());
        participantServiceMaintenance.sendMaintenanceCar(destination);
    }
}
