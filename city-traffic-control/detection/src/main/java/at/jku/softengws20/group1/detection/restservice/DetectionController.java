package at.jku.softengws20.group1.detection.restservice;

import at.jku.softengws20.group1.detection.Map.CityMap;
import at.jku.softengws20.group1.detection.Map.Street;
import at.jku.softengws20.group1.shared.Config;
import at.jku.softengws20.group1.shared.detection.DetectionInterface;
import at.jku.softengws20.group1.shared.impl.model.CarPosition;
import at.jku.softengws20.group1.shared.impl.model.TrafficLightRule;
import at.jku.softengws20.group1.shared.impl.model.TrafficLoad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.*;


@RestController()
@RequestMapping(DetectionInterface.URL)
public class DetectionController implements DetectionInterface<TrafficLoad, TrafficLightRule, CarPosition>, ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ControlSystemService controlSystemService;

    private boolean initialized = false;

    private final CityMap cityMap = new CityMap();
    private final HashMap<String, String> carPosition = new HashMap<>();

    public CityMap getCityMap() {       //for Testcase
        return cityMap;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Thread t = new Thread(() -> {
                cityMap.createCityMap(controlSystemService.getRoadNetwork());
                initialized = true;
                System.out.println("Detection running");
        });
        t.start();
    }



    @Override   //request from Control system
    @GetMapping(DetectionInterface.GET_TRAFFIC_LOAD_URL)
    public at.jku.softengws20.group1.shared.impl.model.TrafficLoad[] getTrafficLoad() {

        List<TrafficLoad> trafficLoad = new LinkedList<>();
        for (Map.Entry<String, Street> entry : cityMap.getStreets().entrySet()) {
            trafficLoad.add(entry.getValue().getTrafficLoad());
        }
        return trafficLoad.toArray(new at.jku.softengws20.group1.shared.impl.model.TrafficLoad[trafficLoad.size()]);
    }

    @Override       //set from Control system
    @PostMapping(DetectionInterface.SET_TRAFFIC_LIGHT_RULES_URL)
    public void setTrafficLightRules(@RequestBody TrafficLightRule[] rules) {
        if (initialized) {
            for (final TrafficLightRule rule : rules) {
                cityMap.getCrossroad(rule.getCrossingId()).getTrafficLight().setPriority(rule.getIncomingRoadSegmentId(), rule.getPriority());
            }
        }
    }

    @Override       //set from Participants
    @PostMapping(DetectionInterface.SET_CAR_POSITION)
    public void setCarPosition(@RequestBody CarPosition position) {
        if (carPosition.containsKey(position.getCarId())) {
            cityMap.getStreet(carPosition.get(position.getIncomingRoadSegmentId())).outgoingVehicle();
        }
        if(position.getIncomingRoadSegmentId() == null) {
            carPosition.remove(position.getCarId());
        } else {
            cityMap.getStreet(position.getIncomingRoadSegmentId()).incomingVehicle();
            carPosition.put(position.getCarId(), position.getIncomingRoadSegmentId());
        }
    }
}
