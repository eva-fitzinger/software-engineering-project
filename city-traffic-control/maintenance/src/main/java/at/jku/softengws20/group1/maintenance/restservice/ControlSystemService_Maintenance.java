package at.jku.softengws20.group1.maintenance.restservice;

import at.jku.softengws20.group1.shared.impl.model.RoadNetwork;
import at.jku.softengws20.group1.shared.impl.model.RoadSegmentStatus;
import at.jku.softengws20.group1.shared.impl.service.ControlSystemRestService;
import org.springframework.stereotype.Service;

/**
 * REST API connecting to  <a href="#{@link}">{@link ControlSystemRestService}</a>
 */

@Service
public class ControlSystemService_Maintenance extends ControlSystemRestService<RoadNetwork, RoadSegmentStatus> {
    public ControlSystemService_Maintenance() {
        super(RoadNetwork.class, RoadSegmentStatus[].class);
    }

}
