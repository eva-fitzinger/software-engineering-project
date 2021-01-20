package at.jku.softengws20.group1.maintenance.restservice;
/*
import at.jku.softengws20.group1.maintenance.dummy.data.DummyRegularRepair;
import at.jku.softengws20.group1.maintenance.impl.ContextNameService;
import at.jku.softengws20.group1.maintenance.impl.RegularRepair;
import at.jku.softengws20.group1.maintenance.impl.Vehicle;
import at.jku.softengws20.group1.maintenance.impl.VehicleCenter;
import at.jku.softengws20.group1.shared.TestMap;
import at.jku.softengws20.group1.shared.impl.model.MaintenanceRequest;
import at.jku.softengws20.group1.shared.impl.model.Timeslot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.servlet.ServletContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = {MaintenanceControllerTest.Config.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class MaintenanceControllerTest {

    @SpringBootApplication(scanBasePackages = {"at.jku.softengws20.group1.maintenance"})
    static class Config extends SpringBootServletInitializer {
        public static void main(String[] args) {
            SpringApplication.run(Config.class, args);
        }
    }

    @Mock
    private ControlSystemService_Maintenance controlSystem;

    @Mock
    private ContextNameService contextNameService;

    @InjectMocks
    private MaintenanceController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(controlSystem.getRoadNetwork()).thenReturn(TestMap.loadDummyMap());
        when(contextNameService.getContextPath()).thenReturn("");
        controller.onApplicationEvent(null);
    }

    @Test
    void notifyApprovedMaintenance() {
        RegularRepair regularRepair = DummyRegularRepair.getRegularRepair();
        MaintenanceRequest<Timeslot> maintenanceRequest1 = new MaintenanceRequest<>(
                regularRepair.getRepairId(),
                regularRepair.getRepairType().toString(),
                regularRepair.getLocation(),
                new Timeslot[]{
                        (Timeslot) DummyRegularRepair.getDummyTimeSlot(regularRepair),
                        (Timeslot) DummyRegularRepair.getDummyTimeSlot(regularRepair)
                });
        MaintenanceRequest<Timeslot> maintenanceRequest2 = new MaintenanceRequest<>(
                regularRepair.getRepairId(),
                regularRepair.getRepairType().toString(),
                regularRepair.getLocation(),
                new Timeslot[]{
                        (Timeslot) DummyRegularRepair.getDummyTimeSlot(regularRepair),
                        (Timeslot) DummyRegularRepair.getDummyTimeSlot(regularRepair)
                });

        // no approved maintenance requests
        assertEquals(0, controller
                .getSchedulingSystem()
                .getSchedule()
                .size());

        // one maintenance requests
        controller.notifyApprovedMaintenance(maintenanceRequest1);
        assertEquals(1, controller
                .getSchedulingSystem()
                .getSchedule()
                .size());

        // two maintenance requests
        controller.notifyApprovedMaintenance(maintenanceRequest2);
        assertEquals(2, controller
                .getSchedulingSystem()
                .getSchedule()
                .size());
    }

    @Test
    void notifyMaintenanceCarArrived() {
        String id = "VH1";

        // car is at maintenance sight
        controller.notifyMaintenanceCarArrived(id);
        Vehicle car = VehicleCenter.getVehicles().stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .orElse(null);

        assertNotNull(car);
        assertFalse(car.isAvailable());
        assertTrue(car.isCarIsGoingOut());

        // car is back in home base
        controller.notifyMaintenanceCarArrived(id);
        car = VehicleCenter.getVehicles().stream()
                .filter(x -> x.getId().equals(id))
                .findFirst()
                .orElse(null);

        assertNotNull(car);
        assertFalse(car.isAvailable());
        assertFalse(car.isCarIsGoingOut());
    }
}*/