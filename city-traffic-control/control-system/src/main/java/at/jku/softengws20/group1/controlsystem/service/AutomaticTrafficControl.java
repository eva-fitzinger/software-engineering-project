package at.jku.softengws20.group1.controlsystem.service;

import at.jku.softengws20.group1.controlsystem.restservice.MaintenanceService;
import at.jku.softengws20.group1.shared.detection.TrafficLoad;
import at.jku.softengws20.group1.shared.impl.model.*;

import at.jku.softengws20.group1.controlsystem.internal.TrafficScenario;
import at.jku.softengws20.group1.controlsystem.restservice.DetectionService;
import at.jku.softengws20.group1.shared.controlsystem.MaintenanceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Random;

@Service
public class AutomaticTrafficControl {
    @Autowired
    private MapRepository mapRepository;

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

    @Scheduled(fixedRate = 1000)
    private void processTrafficLoad() {
        trafficStatusRepository.processTrafficLoad(detectionService.getTrafficLoad());
    }

    @Scheduled(fixedRate = 1000)
    private void processApprovedMaintenanceRequests() {
        // read actual closed roadSegments
        ArrayList<RoadSegmentStatus> closedRoadSegments = trafficStatusRepository.getClosedRoadSegments();
        for (MaintenanceRequest m: maintenanceRepository.getCurrentMaintenanceRequests()) {
            trafficStatusRepository.closeRoadSegment(mapRepository.getRoadSegment(m.getRoadSegmentId()));
            closedRoadSegments.removeIf(r -> r.getRoadSegmentId() == m.getRoadSegmentId());
        }

        //open remaining
        for (at.jku.softengws20.group1.shared.impl.model.RoadSegmentStatus s: closedRoadSegments) {
            s.open();
        }
    }

    @Scheduled(fixedRate = 1000)
    private void setEnabledTrafficLightRules() {
        detectionService.setTrafficLightRules(trafficScenarioRepository.getEnabledTrafficLightRules());
    }
}
