package at.jku.softengws20.group1.controlsystem.restservice;

import at.jku.softengws20.group1.controlsystem.service.MapRepository;
import at.jku.softengws20.group1.shared.controlsystem.ControlSystemInterface;
import at.jku.softengws20.group1.shared.controlsystem.MaintenanceRequest;
import at.jku.softengws20.group1.shared.controlsystem.RoadNetwork;
import at.jku.softengws20.group1.shared.controlsystem.RoadSegmentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ControlSystemInterface.URL)
public class ControlSystemController implements ControlSystemInterface {

    @Autowired
    private MapRepository mapRepository;

    @Override
    @GetMapping(ControlSystemInterface.GET_ROAD_NETWORK_URL)
    public RoadNetwork getRoadNetwork() {
        return mapRepository.getRoadNetwork();
    }

    @Override
    @GetMapping(ControlSystemInterface.GET_STATUS_URL)
    public RoadSegmentStatus[] getStatus() {
        return new RoadSegmentStatus[0];
    }

    @Override
    @PostMapping(ControlSystemInterface.REQUEST_ROAD_CLOSING_URL)
    public void requestRoadClosing(@RequestBody MaintenanceRequest request) {

    }

    @Override
    @PostMapping(ControlSystemInterface.SET_ROAD_AVAILABLE_URL)
    public void setRoadAvailable(@RequestBody String roadSegmentId) {

    }
}