package at.jku.softengws20.group1.detection.restservice;

import at.jku.softengws20.group1.detection.Map.CityMap;
import at.jku.softengws20.group1.shared.impl.model.CarPosition;
import at.jku.softengws20.group1.shared.impl.model.TrafficLightRule;
import at.jku.softengws20.group1.shared.impl.model.TrafficLoad;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;


import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class DetectionControllerTest {

    @InjectMocks
    DetectionController detectionController;

    @InjectMocks
    ControlSystemService controlSystemService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        MockitoAnnotations.openMocks(detectionController);
    }

    @Test
    void getCityMap() throws InterruptedException {
        detectionController.onApplicationEvent_Debug(controlSystemService);
        Thread.sleep(5000);     // wait for DetectionController to initialize

        assertNotNull(detectionController.getCityMap());
    }

    @Test
    void getTrafficLoad() throws InterruptedException {
        detectionController.onApplicationEvent_Debug(controlSystemService);
        Thread.sleep(5000);     // wait for DetectionController to initialize
        assertEquals( detectionController.getCityMap().getStreets().size(), detectionController.getTrafficLoad().length);
    }

    @Test
    void setTrafficLightRules() throws InterruptedException {
        double prioForChangedStreet = 0.8;
        detectionController.onApplicationEvent_Debug(controlSystemService);
        String crossingId = controlSystemService.getRoadNetwork().getCrossings()[0].getId();
        String incomingRoadSegmentId = controlSystemService.getRoadNetwork().getCrossings()[0].getRoadSegmentIds()[0];
        TrafficLightRule[] rules = new TrafficLightRule[1];

        rules[0] = new TrafficLightRule(crossingId, incomingRoadSegmentId, prioForChangedStreet);
        Thread.sleep(5000);     // wait for DetectionController to initialize
        detectionController.setTrafficLightRules(rules);
        CityMap map = detectionController.getCityMap();
        Map<String, Double> prio =  map.getCrossroad(crossingId).getTrafficLight().getRules_debug();
        assertEquals((double) prioForChangedStreet,  (double) prio.get(incomingRoadSegmentId));

    }

    @Test           //check setCarPosition and Traffic load
    void setCarPosition() {
        //init
        int expectationWaitingCars = 18;
        detectionController.onApplicationEvent_Debug(controlSystemService);
        String crossingId = controlSystemService.getRoadNetwork().getCrossings()[0].getId();
        String incomingRoadSegmentId = controlSystemService.getRoadNetwork().getCrossings()[0].getRoadSegmentIds()[0];
        CarPosition[] carPositions = new CarPosition[expectationWaitingCars];

        //set car positions
        for (int i = 0; i < carPositions.length; i++) {
            carPositions[i] = new CarPosition(String.valueOf(i), crossingId, incomingRoadSegmentId);
        }
        detectionController.setCarPosition(carPositions);

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

        crossingId = controlSystemService.getRoadNetwork().getCrossings()[1].getId();
        incomingRoadSegmentId = controlSystemService.getRoadNetwork().getCrossings()[1].getRoadSegmentIds()[0];


        expectationWaitingCars -= 5;
        for (int i = 0; i < carPositions.length-5; i++) {
            carPositions[i] = new CarPosition(String.valueOf(i), crossingId, incomingRoadSegmentId);
        }
        detectionController.setCarPosition(carPositions);
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