package at.jku.softengws20.group1.detection.restservice;

import at.jku.softengws20.group1.interfaces.detection.CarPosition;
import at.jku.softengws20.group1.interfaces.detection.DetectionInterface;
import at.jku.softengws20.group1.interfaces.detection.TrafficLightRule;
import at.jku.softengws20.group1.interfaces.detection.TrafficLoad;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(DetectionInterface.URL)
public class DetectionController implements DetectionInterface {
    @Override
    @GetMapping(DetectionInterface.GET_TRAFFIC_LOAD_URL)
    public TrafficLoad[] getTrafficLoad() { return null; }

    @Override
    @PostMapping(DetectionInterface.SET_TRAFFIC_LIGHT_RULES_URL)
    public void setTrafficLightRules(TrafficLightRule[] rules) {

    }

    @Override
    @PostMapping(DetectionInterface.REGISTER_CAR_FOR_TRAFFIC_LIGHT_URL)
    public void registerCarForTrafficLight(CarPosition position) {

    }
}
