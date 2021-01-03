package at.jku.softengws20.group1.maintenance.impl;

import at.jku.softengws20.group1.maintenance.restservice.ParticipantService_Maintenance;
import at.jku.softengws20.group1.shared.maintenance.MaintenanceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    public @Nullable
    Vehicle sendCar(Repair repair) {
        Random rand = new Random();
        int localNumVehicles = nrVehicles;
        localNumVehicles -= repair.getNrVehiclesNeeded();
        if (localNumVehicles >= 0) {
            // todo should find the right car
            String destination = cityMapService.getRoadNetwork()
                    .getRoadSegments()[rand.nextInt(cityMapService.getRoadNetwork().getRoadSegments().length)].getId();
            for (int i = 0; i < repair.getNrVehiclesNeeded(); i++) {
                System.out.println("Maintenance:: send car");
                Vehicle vehicle = vehicles.get(i); //TODO
                at.jku.softengws20.group1.shared.impl.model.CarPath carPath =
                        new at.jku.softengws20.group1.shared.impl.model.CarPath(
                                cityMapService.getRoadNetwork().getMaintenanceCenterRoadSegmentId(),
                                0,
                                destination,
                                0,
                                getMaintenanceUriString(vehicle));

                participantServiceMaintenance.sendCar(carPath);
                vehicle.setDestination(destination);
            }
            nrVehicles = localNumVehicles;
            return vehicles.stream().filter(Vehicle::isAvailable).findFirst().orElse(null);
        }
        return null;
    }

    public static int getNrVehicles() {
        return nrVehicles;
    }

    public void triggerCarArrived(String id) {
        Vehicle vehicle = vehicles.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
        if(vehicle != null){
            returnCar(vehicle);
        } else {
            System.out.println("Maintenance:: not found vehicle that arrived");
        }
    }

    public void returnCar(Vehicle vehicle) {
        nrVehicles++;
        System.out.println("Maintenance:: return car");
        participantServiceMaintenance.sendCar(new at.jku.softengws20.group1.shared.impl.model.
                CarPath(vehicle.getDestination(),
                0,
                cityMapService.getRoadNetwork().getMaintenanceCenterRoadSegmentId(),
                0,
                getMaintenanceUriString(vehicle)));
    }

    private String getMaintenanceUriString(Vehicle vehicle) {
        return "http://localhost:8080"+servletContext.getContextPath() + MaintenanceInterface.URL + "/"
                + MaintenanceInterface.NOTIFY_MAINTENANCE_CAR_ARRIVED_URL
                + "/" + vehicle.getId();
    }
}
