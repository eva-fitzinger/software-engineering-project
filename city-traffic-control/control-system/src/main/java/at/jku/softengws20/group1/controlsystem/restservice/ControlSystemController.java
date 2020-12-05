package at.jku.softengws20.group1.controlsystem.restservice;

import at.jku.softengws20.group1.controlsystem.service.MapRepository;
import at.jku.softengws20.group1.interfaces.controlsystem.ControlSystemInterface;
import at.jku.softengws20.group1.interfaces.controlsystem.MaintenanceRequest;
import at.jku.softengws20.group1.interfaces.controlsystem.RoadNetwork;
import at.jku.softengws20.group1.interfaces.controlsystem.RoadSegmentStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/controlsystem")
public class ControlSystemController implements ControlSystemInterface {

    @Autowired
    MapRepository mapRepository;

    @Override
    @GetMapping("roadNetwork")
    public RoadNetwork getRoadNetwork() {
        return mapRepository.getRoadNetwork();
    }

    @Override
    @GetMapping("status")
    public RoadSegmentStatus[] getStatus() {
        return new RoadSegmentStatus[0];
    }

    @Override
    @PostMapping("at/jku/softengws20/group1/interfaces/maintenance")
    public void requestRoadClosing(@RequestBody MaintenanceRequest request) {

    }

    @Override
    @PostMapping("setRoadAvailable")
    public void setRoadAvailable(@RequestBody String roadSegmentId) {

    }
}