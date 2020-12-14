package at.jku.softengws20.group1.participants.restservice;

import at.jku.softengws20.group1.shared.impl.model.RoadNetwork;
import at.jku.softengws20.group1.shared.impl.model.RoadSegmentStatus;
import at.jku.softengws20.group1.shared.impl.service.ControlSystemRestService;
import org.springframework.stereotype.Service;

@Service
public class ParticipantsControlSystemService extends ControlSystemRestService<RoadNetwork, RoadSegmentStatus> {
    public ParticipantsControlSystemService() {
        super(RoadNetwork.class, RoadSegmentStatus[].class);
    }
}
