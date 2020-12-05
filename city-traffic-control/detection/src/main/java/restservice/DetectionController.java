package restservice;

import detection.CarPosition;
import detection.DetectionInterface;
import detection.TrafficLightRule;
import detection.TrafficLoad;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/detection")
public class DetectionController implements DetectionInterface {
    @Override
    @GetMapping("trafficLoad")
    public TrafficLoad[] getTrafficLoad() {
        return new TrafficLoad[0];
    }

    @Override
    @PostMapping("setTrafficLightRules")
    public void setTrafficLightRules(TrafficLightRule[] rules) {

    }

    @Override
    @PostMapping("setCarPosition")
    public void registerCarForTrafficLight(CarPosition position) {

    }
}
