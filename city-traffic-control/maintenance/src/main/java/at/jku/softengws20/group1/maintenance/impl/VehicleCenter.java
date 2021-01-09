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

import static at.jku.softengws20.group1.shared.Config.*;

@Service
public class VehicleCenter {

    @Autowired
    private CityMapService cityMapService;

    @Autowired
    private ServletContext servletContext;

    private static int nrVehicles = MAX_MAINTENANCE_VEHICLES;
    private ParticipantService_Maintenance participantServiceMaintenance = new ParticipantService_Maintenance();
    private static List<Vehicle> vehicles = new ArrayList<>();
    private static int vehicleId;

    VehicleCenter() {
        for (int i = 0; i < nrVehicles; i++) {
            vehicles.add(new Vehicle("VH" + vehicleId));
            vehicleId++;
        }
    }

    public @Nullable
    void sendCar(Repair repair) {
        Random rand = new Random();
        Vehicle vehicle = null;
        int localNumVehicles = vehicles.size();
        localNumVehicles -= repair.getNrVehiclesNeeded();
        if (localNumVehicles >= 0) {
            String destination = cityMapService.getRoadNetwork()
                    .getRoadSegments()[rand.nextInt(cityMapService.getRoadNetwork().getRoadSegments().length)].getId();
            System.out.println("Maintenance:: send cars: " + repair.getNrVehiclesNeeded());
            for (int i = 0; i < repair.getNrVehiclesNeeded(); i++) {
                vehicle = vehicles.stream().filter(Vehicle::isAvailable).findFirst().orElse(null);
                if (vehicle == null) {
                    System.out.println("Maintenance:: could not find vehicle to send");
                    return;
                }
                vehicle.setDestination(destination);
                vehicle.setAvailable(false);

                System.out.println("Maintenance:: send car: " + vehicle.getId());
                at.jku.softengws20.group1.shared.impl.model.CarPath carPath =
                        new at.jku.softengws20.group1.shared.impl.model.CarPath(
                                cityMapService.getRoadNetwork().getMaintenanceCenterRoadSegmentId(),
                                0,
                                destination,
                                0,
                                getMaintenanceUriString(vehicle));

                participantServiceMaintenance.sendCar(carPath);
                vehicle.setCarOut(true);
            }
            nrVehicles = localNumVehicles;
        }
    }

    public static int getNrVehicles() {
        return nrVehicles;
    }

    public void triggerCarArrived(String id) {
        Vehicle vehicle = vehicles.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
        if (vehicle != null && vehicle.isCarOut()) {
            System.out.println("Maintenance:: car arrived: " + vehicle.getId());
            returnCar(vehicle);
            vehicle.setCarOut(false);
            vehicle.setAvailable(true);
        } else if (vehicle != null && !vehicle.isCarOut()) {
            System.out.println("Maintenance:: car arrived at VehicleCenter: " + vehicle.getId());
        } else {
            System.out.println("Maintenance:: could not find vehicle that arrived");
        }
    }

    public void returnCar(Vehicle vehicle) {
        System.out.println("Maintenance:: return car: " + vehicle.getId());
        participantServiceMaintenance.sendCar(new at.jku.softengws20.group1.shared.impl.model.
                CarPath(vehicle.getDestination(),
                0,
                cityMapService.getRoadNetwork().getMaintenanceCenterRoadSegmentId(),
                0,
                getMaintenanceUriString(vehicle)));
        nrVehicles++;
    }

    private String getMaintenanceUriString(Vehicle vehicle) {
        return "http://localhost:8080" + servletContext.getContextPath() + MaintenanceInterface.URL + "/"
                + MaintenanceInterface.NOTIFY_MAINTENANCE_CAR_ARRIVED_URL
                + "/" + vehicle.getId();
    }
}
