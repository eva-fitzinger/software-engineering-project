package at.jku.softengws20.group1.controlsystem.restservice;

import at.jku.softengws20.group1.controlsystem.service.MapRepository;
import at.jku.softengws20.group1.shared.TestMap;
import at.jku.softengws20.group1.shared.impl.model.MaintenanceRequest;
import at.jku.softengws20.group1.shared.impl.model.RoadNetwork;
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

import java.util.Arrays;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ControlSystemControllerTest.Config.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ControlSystemControllerTest {

    @SpringBootApplication(scanBasePackages = {"at.jku.softengws20.group1.controlsystem"})
    //@EnableScheduling
    static class Config extends SpringBootServletInitializer {
        public static void main(String[] args) {
            SpringApplication.run(Config.class, args);
        }
    }

    @Mock
    private MapRepository mapRepository;

    @Autowired
    @InjectMocks
    private ControlSystemController controller;

    @Test
    void whenRequestMap_thenMapIsReturned() {
        RoadNetwork rn = new RoadNetwork();
        when(mapRepository.getRoadNetwork()).thenReturn(rn);
        assertEquals(rn, controller.getRoadNetwork());
    }

    @Test
    void maintenanceRequestReceive_thenStoredInMaintenanceRequestToApprove() {
        Timeslot[] timeslots = new Timeslot[] {
            new Timeslot(new Date(), new Date()),
            new Timeslot(new Date(), new Date())
        };
        MaintenanceRequest maintenanceRequest = new MaintenanceRequest("ring", "regular","rs1", timeslots);
        MaintenanceRequest[] maintenanceRequestsToApprove = new MaintenanceRequest[] {maintenanceRequest};

        controller.requestRoadClosing(maintenanceRequest);
        MaintenanceRequest[] maintenanceRequests = controller.getMaintenanceRequests();

        assertEquals(1,
                Arrays.stream(maintenanceRequests)
                .filter(r -> r.getRequestId().equals(maintenanceRequest.getRequestId()))
                .count());
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        RoadNetwork rn = TestMap.loadDummyMap();
        when(mapRepository.getRoadNetwork()).thenReturn(rn);
    }
}