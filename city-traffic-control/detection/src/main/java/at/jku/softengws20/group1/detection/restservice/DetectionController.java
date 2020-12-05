package at.jku.softengws20.group1.detection.restservice;

import at.jku.softengws20.group1.interfaces.detection.CarPosition;
import at.jku.softengws20.group1.interfaces.detection.DetectionInterface;
import at.jku.softengws20.group1.interfaces.detection.TrafficLightRule;
import at.jku.softengws20.group1.interfaces.detection.TrafficLoad;
import at.jku.softengws20.group1.interfaces.impl.model.RoadNetwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping(DetectionInterface.URL)
public class DetectionController implements DetectionInterface {

    @Autowired
    private ControlSystemService controlSystemService;

    @Override
    @GetMapping(DetectionInterface.GET_TRAFFIC_LOAD_URL)
    public TrafficLoad[] getTrafficLoad() {
        RoadNetwork network = controlSystemService.getRoadNetwork();
        for (int i = 0; i < network.getRoadSegments().length; i++) {
            System.out.println(network.getRoadSegments()[i].getId());
        }
        return null;
    }

    @Override
    @PostMapping(DetectionInterface.SET_TRAFFIC_LIGHT_RULES_URL)
    public void setTrafficLightRules(TrafficLightRule[] rules) {

    }

    @Override
    @PostMapping(DetectionInterface.REGISTER_CAR_FOR_TRAFFIC_LIGHT_URL)
    public void registerCarForTrafficLight(CarPosition position) {

    }
}
