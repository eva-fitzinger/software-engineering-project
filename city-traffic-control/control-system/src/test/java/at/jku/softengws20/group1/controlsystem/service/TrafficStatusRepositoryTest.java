package at.jku.softengws20.group1.controlsystem.service;

import at.jku.softengws20.group1.shared.TestMap;
import at.jku.softengws20.group1.shared.detection.TrafficLoad;
import at.jku.softengws20.group1.shared.impl.model.RoadNetwork;
import at.jku.softengws20.group1.shared.impl.model.RoadSegment;
import at.jku.softengws20.group1.shared.impl.model.RoadSegmentStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class TrafficStatusRepositoryTest {

    @InjectMocks
    private TrafficStatusRepository trafficStatusRepository;

    @Mock
    private MapRepository mapRepository;

    @Mock
    private TrafficCapacityRepository trafficCapacityRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void closeAndOpenRoad_correctStatus() {
        RoadNetwork rn = TestMap.loadDummyMap();
        when(mapRepository.getRoadNetwork()).thenReturn(rn);
        RoadSegment rs = rn.getRoadSegments()[2];

        trafficStatusRepository.directlyCloseRoadSegment(rs);
        RoadSegmentStatus status = trafficStatusRepository.getRoadSegmentStatus(rs);
        assertNotNull(status);
        assertEquals(rs.getId(), status.getRoadSegmentId());
        assertFalse(status.isOpen());
    }

    @Test
    void setTrafficAmount_correctStatus() {
        RoadNetwork rn = TestMap.loadDummyMap();
        when(mapRepository.getRoadNetwork()).thenReturn(rn);
        RoadSegment rs = rn.getRoadSegments()[1];

        when(mapRepository.getRoadSegment(anyString())).thenReturn(null);
        when(mapRepository.getRoadSegment(rs.getId())).thenReturn(rs);
        when(trafficCapacityRepository.getCapacity(rs)).thenReturn(20);

        trafficStatusRepository.processTrafficLoad(new TrafficLoad[]{
                new at.jku.softengws20.group1.shared.impl.model.TrafficLoad(rs.getId(), 10)});

        RoadSegmentStatus status = trafficStatusRepository.getRoadSegmentStatus(rs);
        assertNotNull(status);
        assertEquals(rs.getId(), status.getRoadSegmentId());
        assertEquals(0.5, status.getTrafficLoad(), 0.0001);

        trafficStatusRepository.processTrafficLoad(new TrafficLoad[]{
                new at.jku.softengws20.group1.shared.impl.model.TrafficLoad("rs5", 0),
                new at.jku.softengws20.group1.shared.impl.model.TrafficLoad("rs6", 30),
                new at.jku.softengws20.group1.shared.impl.model.TrafficLoad(rs.getId(), 2)});

        status = trafficStatusRepository.getRoadSegmentStatus(rs);
        assertNotNull(status);
        assertEquals(rs.getId(), status.getRoadSegmentId());
        assertEquals(0.1, status.getTrafficLoad(), 0.0001);
    }
}