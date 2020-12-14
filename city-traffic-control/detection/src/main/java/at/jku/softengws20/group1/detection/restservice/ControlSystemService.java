package at.jku.softengws20.group1.detection.restservice;

import at.jku.softengws20.group1.shared.impl.service.ControlSystemRestService;
import at.jku.softengws20.group1.shared.impl.model.*;
import org.springframework.stereotype.Service;

@Service
public class ControlSystemService extends ControlSystemRestService<RoadNetwork, RoadSegmentStatus> {
    public ControlSystemService() {
        super(RoadNetwork.class, RoadSegmentStatus[].class);
    }

    @Override
    public RoadNetwork getRoadNetwork() {
        return super.getRoadNetwork();
    }

    @Override
    public RoadSegmentStatus[] getStatus() {
        return super.getStatus();
    }
}
