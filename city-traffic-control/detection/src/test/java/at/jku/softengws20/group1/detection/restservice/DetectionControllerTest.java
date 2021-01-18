package at.jku.softengws20.group1.detection.restservice;

import at.jku.softengws20.group1.detection.Map.CityMap;
import at.jku.softengws20.group1.shared.TestMap;
import at.jku.softengws20.group1.shared.impl.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class DetectionControllerTest {

    @InjectMocks
    private DetectionController detectionController;

    @Mock
    private ControlSystemService controlSystemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(controlSystemService.getRoadNetwork()).thenReturn(TestMap.loadDummyMap());
        detectionController.onApplicationEvent(null);
    }

    @Test
    void getCityMap()  {
        assertNotNull(detectionController.getCityMap());
        assertEquals(TestMap.loadDummyMap().getCrossings().length, detectionController.getCityMap().getCrossroads().size());
        assertEquals(TestMap.loadDummyMap().getRoadSegments().length, detectionController.getCityMap().getStreets().size());
        RoadSegment roadSegment = TestMap.loadDummyMap().getRoadSegments()[7];
        assertEquals(roadSegment.getDefaultSpeedLimit(), detectionController.getCityMap().getStreet(roadSegment.getId()).getSpeedLimit().getSpeedLimit());
    }

    @Test
    void getTrafficLoad() {
        assertEquals(detectionController.getCityMap().getStreets().size(), detectionController.getTrafficLoad().length);
        assertEquals(TestMap.loadDummyMap().getRoadSegments().length, detectionController.getTrafficLoad().length);
        // tested in detail with Unit test: setCarPosition()
    }

    @Test
    void setTrafficLightRules(){
        //init
        double prioForChangedStreet = 0.8;
        String crossingId = controlSystemService.getRoadNetwork().getCrossings()[0].getId();
        String incomingRoadSegmentId = controlSystemService.getRoadNetwork().getCrossings()[0].getRoadSegmentIds()[0];
        TrafficLightRule[] rules = new TrafficLightRule[1];
        CityMap map = detectionController.getCityMap();

        rules[0] = new TrafficLightRule(crossingId, incomingRoadSegmentId, prioForChangedStreet);
        detectionController.setTrafficLightRules(rules);
        Map<String, Double> prio =  map.getCrossroad(crossingId).getTrafficLight().getRules();

        assertEquals(prioForChangedStreet,  (double) prio.get(incomingRoadSegmentId));
    }

    @Test           //check setCarPosition and Traffic load
    void setCarPosition() {
        //init
        int expectationWaitingCars = 18;
        RoadNetwork map = controlSystemService.getRoadNetwork();
        String crossingId = map.getCrossings()[0].getId();
        String incomingRoadSegmentId = map.getCrossings()[0].getRoadSegmentIds()[0];
        CarPosition[] carPositions = new CarPosition[expectationWaitingCars];

        //set car positions
        for (int i = 0; i < carPositions.length; i++) {
            carPositions[i] = new CarPosition(String.valueOf(i), crossingId, incomingRoadSegmentId);
        }
        detectionController.setCarPosition(carPositions);       //API: setCarPosition

        TrafficLoad[] trafficLoad = detectionController.getTrafficLoad();
        int carsWaiting = -1;
        for (final TrafficLoad value : trafficLoad) {
            if (value.getRoadSegmentId().equals(carPositions[0].getIncomingRoadSegmentId())) {
                carsWaiting = value.getCarsWaiting();
                break;
            }
        }

        //check incoming cars working
        assertEquals(expectationWaitingCars, carsWaiting);

        //change some car positions
        crossingId = map.getCrossings()[2].getId();
        incomingRoadSegmentId = map.getCrossings()[2].getRoadSegmentIds()[0];

        expectationWaitingCars -= 5;
        for (int i = 0; i < carPositions.length-5; i++) {
            carPositions[i] = new CarPosition(String.valueOf(i), crossingId, incomingRoadSegmentId);
        }
        detectionController.setCarPosition(carPositions);           //API: setCarPosition
        trafficLoad = detectionController.getTrafficLoad();
        carsWaiting = -1;
        for (final TrafficLoad load : trafficLoad) {
            if (load.getRoadSegmentId().equals(carPositions[0].getIncomingRoadSegmentId())) {
                carsWaiting = load.getCarsWaiting();
                break;
            }
        }

        //check outgoing cars working
        assertEquals(expectationWaitingCars, carsWaiting);
    }
}