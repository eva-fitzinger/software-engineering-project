package at.jku.softengws20.group1.maintenance.restservice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

class MaintenanceControllerTest {

    @InjectMocks
    private ControlSystemService_Maintenance controlSystem;
    @InjectMocks
    private ParticipantService_Maintenance participantSystem;
    @InjectMocks
    private MaintenanceController controller;

//    @Test
//    void whenRequestMap_thenMapIsReturned() {
//        RoadNetwork rn = new RoadNetwork();
//        when(mapRepository.getRoadNetwork()).thenReturn(rn);
//        assertEquals(rn, controller.getRoadNetwork());
//    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        MockitoAnnotations.openMocks(controller);
    }

    @Test
    void notifyApprovedMaintenance() {

    }

    @Test
    void notifyMaintenanceCarArrived() {

    }

    @Test
    void onApplicationEvent() {

    }
}