package at.jku.softengws20.group1.maintenance.restservice;

import at.jku.softengws20.group1.shared.controlsystem.ControlSystemInterface;
import at.jku.softengws20.group1.shared.controlsystem.MaintenanceRequest;
import at.jku.softengws20.group1.shared.impl.model.RoadNetwork;
import at.jku.softengws20.group1.shared.impl.model.RoadSegmentStatus;
import at.jku.softengws20.group1.shared.impl.service.ControlSystemRestService;
import org.springframework.stereotype.Service;

@Service
public class ControlSystemService_Maintenance extends ControlSystemRestService<RoadNetwork, RoadSegmentStatus> {
    public ControlSystemService_Maintenance() {
        super(RoadNetwork.class, RoadSegmentStatus[].class);
    }

    @Override
    public void requestRoadClosing(MaintenanceRequest request) {
        post(ControlSystemInterface.REQUEST_ROAD_CLOSING_URL, request);
    }

    @Override
    public void setRoadAvailable(String roadSegmentId) {
        post(ControlSystemInterface.SET_ROAD_AVAILABLE_URL, roadSegmentId);
    }
}
