package at.jku.softengws20.group1.detection.restservice;

import at.jku.softengws20.group1.shared.impl.model.BasicRoadNetwork;
import at.jku.softengws20.group1.shared.impl.model.RoadSegmentStatus;
import at.jku.softengws20.group1.shared.impl.service.ControlSystemRestService;
import org.springframework.stereotype.Service;

@Service
public class ControlSystemService extends ControlSystemRestService<BasicRoadNetwork, RoadSegmentStatus> {
    public ControlSystemService() {
        super(BasicRoadNetwork.class, RoadSegmentStatus[].class);
    }
}
