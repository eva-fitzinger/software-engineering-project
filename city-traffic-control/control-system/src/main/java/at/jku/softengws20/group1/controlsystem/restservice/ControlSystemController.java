package at.jku.softengws20.group1.controlsystem.restservice;

import at.jku.softengws20.group1.controlsystem.internal.TrafficScenario;
import at.jku.softengws20.group1.controlsystem.service.*;
import at.jku.softengws20.group1.shared.controlsystem.ControlSystemInterface;
import at.jku.softengws20.group1.shared.controlsystem.MaintenanceRequest;
import at.jku.softengws20.group1.shared.controlsystem.RoadNetwork;
import at.jku.softengws20.group1.shared.controlsystem.RoadSegmentStatus;
import at.jku.softengws20.group1.shared.impl.model.RoadSegment;
import at.jku.softengws20.group1.shared.impl.model.TrafficLightRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


@RestController
@RequestMapping(ControlSystemInterface.URL)
public class ControlSystemController implements ControlSystemInterface {

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
        maintenanceRepository.pushMaintenanceRequestToApprove((at.jku.softengws20.group1.shared.impl.model.MaintenanceRequest) request);
    }

    @Override
    @PostMapping(ControlSystemInterface.SET_ROAD_AVAILABLE_URL)
    public void setRoadAvailable(@RequestBody String roadSegmentId) {
        trafficStatusRepository.openRoadSegment(mapRepository.getRoadSegment(roadSegmentId));
    }

    private final String GET_ENABLED_TRAFFIC_SCENARIOS_URL = "getEnabledTrafficScenarios";
    @GetMapping(GET_ENABLED_TRAFFIC_SCENARIOS_URL)
    public TrafficScenario[] getEnabledTrafficScenarios() {
       return  trafficScenarioRepository.getEnabledTrafficScenarios();
    }

    private final String GET_TRAFFIC_CAPACITY_URL = "getTrafficCapacity";
    @GetMapping(GET_TRAFFIC_CAPACITY_URL)
    public String[] getTrafficCapacity() {
        return  trafficCapacityRepository.getCapacityString();
    }

    private final String GET_TRAFFIC_SCENARIOS_URL = "getScenarios";
    @GetMapping(GET_TRAFFIC_SCENARIOS_URL)
    public TrafficScenario[] getTrafficScenarios() {
        return trafficScenarioRepository.getTrafficScenarios();
    }

    private final String SET_APPROVED_MAINTENANCE_URL = "setApprovedMaintenance";
    @PostMapping (SET_APPROVED_MAINTENANCE_URL)
    public void setApprovedMaintenance(@RequestBody MaintenanceRequest maintenanceRequest) {
        maintenanceRepository.pushApprovedMaintenanceRequests((at.jku.softengws20.group1.shared.impl.model.MaintenanceRequest) maintenanceRequest);
    }

    private final String GET_MAINENANCE_REQUESTS_URL = "getMaintenanceRequests";
    @GetMapping(GET_MAINENANCE_REQUESTS_URL)
    public MaintenanceRequest[] getMaintenanceRequests() {
        return maintenanceRepository.getMaintenanceRequestsToApprove();
    }

    private final String GET_ENABLED_TRAFFICLIGHT_RULES_URL = "getEnabledTrafficLightRules";
    @GetMapping(GET_ENABLED_TRAFFICLIGHT_RULES_URL)
    public TrafficLightRule[] getEnabledTrafficLightRules() {
        return trafficScenarioRepository.getEnabledTrafficLightRules();
    }

}