package at.jku.softengws20.group1.detection.restservice;

import at.jku.softengws20.group1.shared.detection.CarPosition;
import at.jku.softengws20.group1.shared.detection.DetectionInterface;
import at.jku.softengws20.group1.shared.detection.TrafficLightRule;
import at.jku.softengws20.group1.shared.impl.model.BasicRoadNetwork;
import at.jku.softengws20.group1.shared.impl.model.TrafficLoad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController()
@RequestMapping(DetectionInterface.URL)
public class DetectionController implements DetectionInterface<TrafficLoad> {

    @Autowired
    private ControlSystemService controlSystemService;

    @Override
    @GetMapping(DetectionInterface.GET_TRAFFIC_LOAD_URL)
    public Collection<TrafficLoad> getTrafficLoad() {
        BasicRoadNetwork network = controlSystemService.getRoadNetwork();
        network.getRoadSegments().forEach(rs -> System.out.println(rs.getId()));
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
