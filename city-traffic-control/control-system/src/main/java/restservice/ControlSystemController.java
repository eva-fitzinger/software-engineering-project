package restservice;

import controlsystem.ControlSystemInterface;
import controlsystem.MaintenanceRequest;
import controlsystem.RoadNetwork;
import controlsystem.RoadSegmentStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/controlsystem")
public class ControlSystemController implements ControlSystemInterface {

    @Override
    @GetMapping("roadNetwork")
    public RoadNetwork getRoadNetwork() {
        return null;
    }

    @Override
    @GetMapping("status")
    public RoadSegmentStatus[] getStatus() {
        return new RoadSegmentStatus[0];
    }

    @Override
    @PostMapping("maintenance")
    public void requestRoadClosing(@RequestBody MaintenanceRequest request) {

    }

    @Override
    @PostMapping("setRoadAvailable")
    public void setRoadAvailable(@RequestBody String roadSegmentId) {

    }
}