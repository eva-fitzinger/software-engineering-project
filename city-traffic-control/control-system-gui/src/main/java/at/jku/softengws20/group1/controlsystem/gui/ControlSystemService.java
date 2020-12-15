package at.jku.softengws20.group1.controlsystem.gui;

import at.jku.softengws20.group1.controlsystem.gui.model.RoadNetwork;
import at.jku.softengws20.group1.shared.impl.model.RoadSegmentStatus;
import at.jku.softengws20.group1.shared.impl.service.ControlSystemRestService;

public class ControlSystemService extends ControlSystemRestService<RoadNetwork, RoadSegmentStatus> {
    public ControlSystemService() {
        super(RoadNetwork.class, RoadSegmentStatus[].class);
    }
}
