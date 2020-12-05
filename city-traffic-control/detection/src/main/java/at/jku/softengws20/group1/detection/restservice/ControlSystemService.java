package at.jku.softengws20.group1.detection.restservice;

import at.jku.softengws20.group1.interfaces.impl.service.ControlSystemRestService;
import at.jku.softengws20.group1.interfaces.impl.model.*;
import org.springframework.stereotype.Service;

@Service
public class ControlSystemService extends ControlSystemRestService<RoadNetwork, RoadSegmentStatus> {
    public ControlSystemService() {
        super(RoadNetwork.class, RoadSegmentStatus[].class);
    }
}
