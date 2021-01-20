package at.jku.softengws20.group1.controlsystem.restservice;

import at.jku.softengws20.group1.controlsystem.service.MaintenanceRepository;
import at.jku.softengws20.group1.controlsystem.service.MapRepository;
import at.jku.softengws20.group1.controlsystem.service.TrafficStatusRepository;
import at.jku.softengws20.group1.shared.TestMap;
import at.jku.softengws20.group1.shared.impl.model.*;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
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

    @Mock
    private MaintenanceService maintenanceService;

    @Autowired
    @InjectMocks
    private ControlSystemController controller;

    @Autowired
    @InjectMocks
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    @InjectMocks
    private TrafficStatusRepository trafficStatusRepository;

    @Test
    void whenRequestMap_thenMapIsReturned() {
        RoadNetwork rn = new RoadNetwork();
        when(mapRepository.getRoadNetwork()).thenReturn(rn);
        assertEquals(rn, controller.getRoadNetwork());
    }

    @Test
    void maintenanceRequestReceived_thenStoredInMaintenanceRequestToApprove() {

        MaintenanceRequest maintenanceRequest1 = new MaintenanceRequest("1", "regular","rs1", new Timeslot[] {
                new Timeslot(new Date(), new Date()),
                new Timeslot(new Date(), new Date())});
        MaintenanceRequest maintenanceRequest2 = new MaintenanceRequest("2", "regular","rs2", new Timeslot[] {
                new Timeslot(new Date(), new Date()),
                new Timeslot(new Date(), new Date())});

        //no maintenance request to approve
        assertEquals(0, controller.getMaintenanceRequests().length);

        // push maintenanceRequest1
        controller.requestRoadClosing(maintenanceRequest1);
        // check number of maintenance requests to approve
        assertEquals(1, controller.getMaintenanceRequests().length);
        // look for maintenanceRequest 1
        assertEquals(1,Arrays.stream(controller.getMaintenanceRequests())
                .filter(r -> r.getRequestId().equals(maintenanceRequest1.getRequestId()))
                .count());

        // push maintenanceRequest1
        controller.requestRoadClosing(maintenanceRequest2);
        // check number of maintenance requests to approve
        assertEquals(2, controller.getMaintenanceRequests().length);
        // look for maintenanceRequest 1
        assertEquals(1, Arrays.stream(controller.getMaintenanceRequests())
                .filter(r -> r.getRequestId().equals(maintenanceRequest1.getRequestId()))
                .count());
        // look for maintenanceRequest 2
        assertEquals(1,Arrays.stream(controller.getMaintenanceRequests())
                .filter(r -> r.getRequestId().equals(maintenanceRequest2.getRequestId()))
                .count());

        // push 98 more maintenance requests
        for (Integer i = 3; i <= 100; i++) {
            controller.requestRoadClosing(new MaintenanceRequest(i.toString(), "regular","rs1", new Timeslot[] { new Timeslot(new Date(), new Date())}));
        }
        // check number of maintenance requests to approve
        assertEquals(100, controller.getMaintenanceRequests().length);
        // each request has to be found in getMaintenanceRequests once
        for (Integer i = 1; i <= 100; i++) {
            final String requestId = i.toString();
            assertEquals(1, Arrays.stream(controller.getMaintenanceRequests())
                    .filter(r -> r.getRequestId().equals(requestId))
                    .count());
        }

    }

    @Test
    void approvalOfMaintenanceRequests() {

        MaintenanceRequest[] maintenanceRequests = new MaintenanceRequest[100];
        for (Integer i = 0; i < 100; i++) {
            maintenanceRequests[i] = new MaintenanceRequest(i.toString(), "regular","rs1", new Timeslot[] {
                    new Timeslot(new Date(), new Date()),
                    new Timeslot(new Date(), new Date())});
        }

        // no maintenance requests to approve
        assertEquals(0, controller.getMaintenanceRequests().length);
        // no approved maintenance request
        assertEquals(0, controller.getApprovedMaintenanceRequests().length);

        // process maintenance request 0
        MaintenanceRequest mr0 = maintenanceRequests[0];

        // push maintenance request 0
        controller.requestRoadClosing(mr0);
        assertEquals(1, controller.getMaintenanceRequests().length);
        assertEquals(0, controller.getApprovedMaintenanceRequests().length);

        assertEquals(1,Arrays.stream(controller.getMaintenanceRequests())
                .filter(r -> r.getRequestId().equals(mr0.getRequestId()))
                .count());

        // approve maintenance request 0
        controller.setApprovedMaintenance(mr0);
        assertEquals(0, controller.getMaintenanceRequests().length);
        assertEquals(1, controller.getApprovedMaintenanceRequests().length);

        assertEquals(1,Arrays.stream(controller.getApprovedMaintenanceRequests())
                .filter(r -> r.getRequestId().equals(mr0.getRequestId()))
                .count());

        // process maintenance request 1
        MaintenanceRequest mr1 = maintenanceRequests[1];

        // push maintenance request 0
        controller.requestRoadClosing(mr1);
        assertEquals(1, controller.getMaintenanceRequests().length);
        assertEquals(1, controller.getApprovedMaintenanceRequests().length);

        assertEquals(1,Arrays.stream(controller.getMaintenanceRequests())
                .filter(r -> r.getRequestId().equals(mr1.getRequestId()))
                .count());
        assertEquals(1,Arrays.stream(controller.getApprovedMaintenanceRequests())
                .filter(r -> r.getRequestId().equals(mr0.getRequestId()))
                .count());

        // approve maintenance request 0
        controller.setApprovedMaintenance(mr1);
        assertEquals(0, controller.getMaintenanceRequests().length);
        assertEquals(2, controller.getApprovedMaintenanceRequests().length);

        assertEquals(1,Arrays.stream(controller.getApprovedMaintenanceRequests())
                .filter(r -> r.getRequestId().equals(mr0.getRequestId()))
                .count());
        assertEquals(1,Arrays.stream(controller.getApprovedMaintenanceRequests())
                .filter(r -> r.getRequestId().equals(mr1.getRequestId()))
                .count());

        // process maintenance request 2 - 49
        // push maintenance request 2 - 49
        for (Integer i = 2; i < 50; i++) {
            controller.requestRoadClosing(maintenanceRequests[i]);
        }
        assertEquals(48, controller.getMaintenanceRequests().length);
        assertEquals(2, controller.getApprovedMaintenanceRequests().length);
        for (Integer i = 2; i < 50; i++) {
            final String mr = i.toString();
            assertEquals(1,Arrays.stream(controller.getMaintenanceRequests())
                    .filter(r -> r.getRequestId().equals(mr))
                    .count());
        }
        for (Integer i = 0; i < 2 ; i++) {
            final String mr = i.toString();
            assertEquals(1,Arrays.stream(controller.getApprovedMaintenanceRequests())
                    .filter(r -> r.getRequestId().equals(mr))
                    .count());
        }
        // approve maintenance request 2 - 49
        for (Integer i = 2; i < 50; i++) {
            controller.setApprovedMaintenance(maintenanceRequests[i]);
        }
        assertEquals(0, controller.getMaintenanceRequests().length);
        assertEquals(50, controller.getApprovedMaintenanceRequests().length);
        for (Integer i = 0; i < 50 ; i++) {
            final String mr = i.toString();
            assertEquals(1,Arrays.stream(controller.getApprovedMaintenanceRequests())
                    .filter(r -> r.getRequestId().equals(mr))
                    .count());
        }

        // process maintenance request 50 - 99
        // push and approve maintenance request 50 - 99
        int numberOfMaintenanceRequestsToApprove = 0;
        int numberOfApprovedMaintenanceRequests = 50;
        for (Integer i = 50; i < 100; i++) {
            controller.requestRoadClosing(maintenanceRequests[i]);
            numberOfMaintenanceRequestsToApprove++;
            assertEquals(numberOfMaintenanceRequestsToApprove, controller.getMaintenanceRequests().length);
            assertEquals(numberOfApprovedMaintenanceRequests, controller.getApprovedMaintenanceRequests().length);
            controller.setApprovedMaintenance(maintenanceRequests[i]);
            numberOfMaintenanceRequestsToApprove--;
            numberOfApprovedMaintenanceRequests++;
            assertEquals(numberOfMaintenanceRequestsToApprove, controller.getMaintenanceRequests().length);
            assertEquals(numberOfApprovedMaintenanceRequests, controller.getApprovedMaintenanceRequests().length);
        }

        // look up for each maintenance request in ApprovedMaintenanceRequests
        for (Integer i = 0; i < 100 ; i++) {
            final String mr = i.toString();
            assertEquals(1,Arrays.stream(controller.getApprovedMaintenanceRequests())
                    .filter(r -> r.getRequestId().equals(mr))
                    .count());
        }

    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        RoadNetwork rn = TestMap.loadDummyMap();
        when(mapRepository.getRoadNetwork()).thenReturn(rn);
        maintenanceRepository.init();
    }
}