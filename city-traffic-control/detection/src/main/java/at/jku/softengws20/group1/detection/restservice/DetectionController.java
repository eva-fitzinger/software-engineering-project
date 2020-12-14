package at.jku.softengws20.group1.detection.restservice;

import at.jku.softengws20.group1.detection.Map.CityMap;
import at.jku.softengws20.group1.detection.Map.Crossroad;
import at.jku.softengws20.group1.shared.detection.CarPosition;
import at.jku.softengws20.group1.shared.detection.DetectionInterface;
import at.jku.softengws20.group1.shared.detection.TrafficLightRule;
import at.jku.softengws20.group1.shared.detection.TrafficLoad;
import at.jku.softengws20.group1.shared.impl.model.RoadNetwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@RestController()
@RequestMapping(DetectionInterface.URL)
public class DetectionController implements DetectionInterface {

    @Autowired
    private ControlSystemService controlSystemService;

    private CityMap cityMap = new CityMap();
    private HashMap<String, CrossingPosition> carPositions = new HashMap<>();

    @PostConstruct
    public void init() {
        Thread t = new Thread(() -> {
            try {
                Thread.sleep(200);
                cityMap.createCityMap(controlSystemService.getRoadNetwork());
                System.out.println("Detection running");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();
    }

    @Override   //request from Control system
    @GetMapping(DetectionInterface.GET_TRAFFIC_LOAD_URL)
    public TrafficLoad[] getTrafficLoad() {
        RoadNetwork network = controlSystemService.getRoadNetwork();
        for (int i = 0; i < network.getRoadSegments().length; i++) {
            System.out.println(network.getRoadSegments()[i].getId());
        }

        List<TrafficLoad> trafficLoad = new LinkedList<>();
        for (Map.Entry<String, Crossroad> entry : cityMap.getCrossroads().entrySet()) {
            trafficLoad.addAll(entry.getValue().getNumberOfVehicles());
        }
        return trafficLoad.toArray(new TrafficLoad[trafficLoad.size()]);
    }

    @Override       //set from Control system
    @PostMapping(DetectionInterface.SET_TRAFFIC_LIGHT_RULES_URL)
    public void setTrafficLightRules(TrafficLightRule[] rules) {
        for (final TrafficLightRule rule : rules) {
            cityMap.getCrossroad(rule.getCrossingId()).getTrafficLight().setPriority(rule.getIncomingRoadSegmentId(), rule.getPriority());
        }
    }

    @Override       //set from Participants
    @PostMapping(DetectionInterface.SET_CAR_POSITION)
    public void setCarPosition(CarPosition position) {
        CrossingPosition old = carPositions.get(position.getCarId());
        if(old != null) {
            cityMap.getCrossroad(old.CrossingId).getTrafficLight().outgoingVehicle(old.RoadSegmentId, position.getCarId());
        }
        carPositions.put(position.getCarId(), new CrossingPosition(position.getCrossingId(), position.getIncomingRoadSegmentId()));
    }

    @Override
    @PostMapping(DetectionInterface.SET_WAITING)
    public void setWaiting(String carId) {
        CrossingPosition position = carPositions.get(carId);
        if(position != null) cityMap.getCrossroad(position.CrossingId).getTrafficLight().incomingVehicle(position.RoadSegmentId, carId);
    }

    private static class CrossingPosition {
        public final String CrossingId;
        public final String RoadSegmentId;

        public CrossingPosition(String crossingId, String roadSegmentId) {
            CrossingId = crossingId;
            RoadSegmentId = roadSegmentId;
        }
    }
}
