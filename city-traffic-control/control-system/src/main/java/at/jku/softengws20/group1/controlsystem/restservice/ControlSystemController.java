package at.jku.softengws20.group1.controlsystem.restservice;

import at.jku.softengws20.group1.controlsystem.internal.TrafficScenario;
import at.jku.softengws20.group1.controlsystem.service.*;
import at.jku.softengws20.group1.shared.controlsystem.ControlSystemInterface;
import at.jku.softengws20.group1.shared.controlsystem.RoadNetwork;
import at.jku.softengws20.group1.shared.impl.model.RoadSegmentStatus;
import at.jku.softengws20.group1.shared.impl.model.MaintenanceRequest;
import at.jku.softengws20.group1.shared.impl.model.RoadSegment;
import at.jku.softengws20.group1.shared.impl.model.TrafficLightRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.Random;


@RestController
@RequestMapping(ControlSystemInterface.URL)
public class ControlSystemController implements ControlSystemInterface<MaintenanceRequest>, ControlSystemGUIInterface<MaintenanceRequest> {

    @Autowired
    private MapRepository mapRepository;

    @Autowired
    private TrafficCapacityRepository trafficCapacityRepository;

    @Autowired
    private TrafficStatusRepository trafficStatusRepository;

    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private TrafficScenarioRepository trafficScenarioRepository;

    @Autowired
    private DetectionService detectionService;

    @Autowired
    private MaintenanceService maintenanceService;

    @Override
    @GetMapping(ControlSystemInterface.GET_ROAD_NETWORK_URL)
    public RoadNetwork getRoadNetwork() {
        return mapRepository.getRoadNetwork();
    }

    @Override
    @GetMapping(ControlSystemInterface.GET_STATUS_URL)
    public RoadSegmentStatus[] getStatus() {
        return trafficStatusRepository.getRoadSegmentStatus();
    }

    @Override
    @PostMapping(ControlSystemInterface.REQUEST_ROAD_CLOSING_URL)
    public void requestRoadClosing (@RequestBody MaintenanceRequest request) {
        maintenanceRepository.pushMaintenanceRequestToApprove(request);
    }

    @Override
    @PostMapping(ControlSystemInterface.SET_ROAD_AVAILABLE_URL)
    public void setRoadAvailable(@RequestBody String roadSegmentId) {
        trafficStatusRepository.directlyOpenRoadSegment(mapRepository.getRoadSegment(roadSegmentId));
    }

    public static final String SET_ROAD_CLOSE = "setRoadClose";
    @PostMapping(SET_ROAD_CLOSE)
    public void setRoadClose(@RequestBody String roadSegmentId) {
        trafficStatusRepository.directlyCloseRoadSegment(mapRepository.getRoadSegment(roadSegmentId));
    }

    public static final String GET_CLOSED_ROADSEGMENTS_URL = "getClosedRoadSegments";
    @GetMapping(GET_CLOSED_ROADSEGMENTS_URL)
    public RoadSegmentStatus[] getClosedRoadSegments() {
        return  trafficStatusRepository.getClosedRoadSegments();
    }


    public static final String GET_ENABLED_TRAFFIC_SCENARIOS_URL = "getEnabledTrafficScenarios";
    @Override
    @GetMapping(GET_ENABLED_TRAFFIC_SCENARIOS_URL)
    public TrafficScenario[] getEnabledTrafficScenarios() {
       return  trafficScenarioRepository.getEnabledTrafficScenarios();
    }

    private static final String GET_TRAFFIC_CAPACITY_URL = "getTrafficCapacity";
    @GetMapping(GET_TRAFFIC_CAPACITY_URL)
    public String[] getTrafficCapacity() {
        return  trafficCapacityRepository.getCapacityString();
    }

    public static final String GET_TRAFFIC_SCENARIOS_URL = "getScenarios";
    @Override
    @GetMapping(GET_TRAFFIC_SCENARIOS_URL)
    public TrafficScenario[] getTrafficScenarios() {
        return trafficScenarioRepository.getTrafficScenarios();
    }

    public static final String SET_APPROVED_MAINTENANCE_URL = "setApprovedMaintenance";
    @Override
    @PostMapping (SET_APPROVED_MAINTENANCE_URL)
    public void setApprovedMaintenance(@RequestBody MaintenanceRequest maintenanceRequest) {
        maintenanceRepository.pushApprovedMaintenanceRequests((at.jku.softengws20.group1.shared.impl.model.MaintenanceRequest) maintenanceRequest);
    }

    public static final String GET_MAINENANCE_REQUESTS_URL = "getMaintenanceRequests";
    @Override
    @GetMapping(GET_MAINENANCE_REQUESTS_URL)
    public MaintenanceRequest[] getMaintenanceRequests() {
        return maintenanceRepository.getMaintenanceRequestsToApprove();
    }

    private static final String GET_ENABLED_TRAFFICLIGHT_RULES_URL = "getEnabledTrafficLightRules";
    @GetMapping(GET_ENABLED_TRAFFICLIGHT_RULES_URL)
    public TrafficLightRule[] getEnabledTrafficLightRules() {
        return trafficScenarioRepository.getEnabledTrafficLightRules();
    }

//    @Scheduled(fixedRate = 1000)
//    private void testRoadClose() {
//        Random rand = new Random();
//        System.out.println("##### Close RoadSegment 10761352_80");
//        if (rand.nextInt(2) % 2 == 0) {
//            setRoadClose("10761352_80");
//        } else {
//            setRoadAvailable("10761352_80");
//        }
//    }

}