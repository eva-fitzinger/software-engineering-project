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
    private static int nrEmployees = MAX_EMPLOYEES;
    private final ParticipantService_Maintenance participantServiceMaintenance = new ParticipantService_Maintenance();
    private static final List<Vehicle> vehicles = new ArrayList<>();
    private static final List<Employee> employees = new ArrayList<>();
    private static int vehicleId;
    private static int employeeId;

    VehicleCenter() {
        for (int i = 0; i < nrVehicles; i++) {
            vehicles.add(new Vehicle("VH" + vehicleId));
            vehicleId++;
        }

        for (int i = 0; i < nrEmployees; i++) {
            employees.add(new Employee("E" + employeeId));
            employeeId++;
        }
    }

    public @Nullable
    void sendCar(Repair repair) {
        Random rand = new Random();
        Vehicle vehicle;
        Employee employee = new Employee("null");
        int localNumVehicles = nrVehicles;
        int localNumEmployees = nrEmployees;

        localNumVehicles -= repair.getNrVehiclesNeeded();
        localNumEmployees -= repair.getNrWorkersNeeded();
        if (localNumVehicles >= 0 && localNumEmployees >= 0) {
            String destination = cityMapService.getRoadNetwork()
                    .getRoadSegments()[rand.nextInt(cityMapService.getRoadNetwork().getRoadSegments().length)].getId();
            System.out.println("Maintenance:: send " + repair.getNrVehiclesNeeded() + " cars");
            System.out.println("Maintenance:: send " + repair.getNrWorkersNeeded() + " employees");

            for (int i = 0; i < repair.getNrWorkersNeeded(); i++) {
                employee = employees.stream().filter(x -> x.getAssignedToCar() == null).findFirst().orElse(null);
                if (employee == null) {
                    System.out.println("Maintenance:: no employee available right now");
                    return;
                }
            }

            for (int i = 0; i < repair.getNrVehiclesNeeded(); i++) {
                vehicle = vehicles.stream().filter(Vehicle::isAvailable).findFirst().orElse(null);
                if (vehicle == null) {
                    System.out.println("Maintenance:: no vehicle available right now");
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
                vehicle.setCarIsGoingOut(true);
                employee.setAssignedToCar(vehicle.getId());
            }
            nrVehicles = localNumVehicles;
            nrEmployees = localNumEmployees;
        }
    }

    public static int getNrVehicles() {
        return nrVehicles;
    }

    public void triggerCarArrived(String id) {
        Vehicle vehicle = vehicles.stream().filter(x -> x.getId().equals(id)).findFirst().orElse(null);
        long numEmployee = employees.stream().filter(x -> id.equals(x.getAssignedToCar())).count();
        if (vehicle != null && vehicle.isCarOut()) {
            System.out.println("Maintenance:: car (" + vehicle.getId() + ") arrived at " + vehicle.getDestination());
            returnCar(vehicle);
            vehicle.setCarIsGoingOut(false);
            vehicle.setAvailable(false);
        } else if (vehicle != null && !vehicle.isCarOut()) {
            vehicle.setAvailable(true);
            System.out.println("Maintenance:: car(" + vehicle.getId() + ") arrived at VehicleCenter");
            nrVehicles++;
            if (numEmployee > 0) {
                for (int i = 0; i < numEmployee; i++) {
                    employees.stream().filter(x -> id.equals(x.getAssignedToCar())).findAny().ifPresent(employee -> {
                        employee.setAssignedToCar(null);
                        System.out.println("Maintenance:: employee(" + employee.getId() + ") arrived at VehicleCenter");
                        nrEmployees++;
                    });

                }
            } else {
                System.out.println("Maintenance:: could not find employee that arrived");
            }
        } else {
            System.out.println("Maintenance:: could not find vehicle that arrived");
        }
    }

    public void returnCar(Vehicle vehicle) {
        System.out.println("Maintenance:: return car (" + vehicle.getId() + ")");
        participantServiceMaintenance.sendCar(new at.jku.softengws20.group1.shared.impl.model.
                CarPath(vehicle.getDestination(),
                0,
                cityMapService.getRoadNetwork().getMaintenanceCenterRoadSegmentId(),
                0,
                getMaintenanceUriString(vehicle)));
    }

    private String getMaintenanceUriString(Vehicle vehicle) {
        return "http://localhost:8080" + servletContext.getContextPath() + MaintenanceInterface.URL + "/"
                + MaintenanceInterface.NOTIFY_MAINTENANCE_CAR_ARRIVED_URL
                + "/" + vehicle.getId();
    }
}
