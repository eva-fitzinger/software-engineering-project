package at.jku.softengws20.group1.controlsystem.restservice;

import at.jku.softengws20.group1.shared.controlsystem.RoadNetwork;
import at.jku.softengws20.group1.shared.controlsystem.RoadSegmentStatus;
import at.jku.softengws20.group1.shared.impl.model.TrafficLoad;
import at.jku.softengws20.group1.shared.impl.service.DetectionRestService;
import org.springframework.stereotype.Service;

@Service
public class DetectionService extends DetectionRestService<TrafficLoad> {

    public DetectionService() {
        super(TrafficLoad[].class);
    }

    @Override
    public TrafficLoad[] getTrafficLoad() {
        return super.getTrafficLoad();
    }
}
