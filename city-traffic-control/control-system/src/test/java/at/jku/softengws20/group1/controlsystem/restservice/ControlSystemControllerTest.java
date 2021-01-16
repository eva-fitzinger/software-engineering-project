package at.jku.softengws20.group1.controlsystem.restservice;

import at.jku.softengws20.group1.controlsystem.service.MapRepository;
import at.jku.softengws20.group1.shared.impl.model.RoadNetwork;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ControlSystemControllerTest {

    @Mock
    private MapRepository mapRepository;

    @InjectMocks
    private ControlSystemController controller;

    @Test
    void whenRequestMap_thenMapIsReturned() {
        RoadNetwork rn = new RoadNetwork();
        when(mapRepository.getRoadNetwork()).thenReturn(rn);
        assertEquals(rn, controller.getRoadNetwork());
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
}