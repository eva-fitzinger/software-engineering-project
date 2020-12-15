package at.jku.softengws20.group1.participants.restservice;

import at.jku.softengws20.group1.participants.navigation.Navigation;
import at.jku.softengws20.group1.participants.roadNetwork.*;
import at.jku.softengws20.group1.participants.simulation.Participant;
import at.jku.softengws20.group1.participants.simulation.Simulation;
import at.jku.softengws20.group1.shared.controlsystem.RoadSegment;
import at.jku.softengws20.group1.shared.impl.model.TrafficLightChange;
import at.jku.softengws20.group1.shared.maintenance.CarPath;
import at.jku.softengws20.group1.shared.participants.ParticipantsInterface;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping(ParticipantsInterface.URL)
public class ParticipantsController implements ParticipantsInterface, ApplicationListener<ContextRefreshedEvent> {
    private final ParticipantsControlSystemService controlSystemService = new ParticipantsControlSystemService();
    private final Navigation navigation;
    private final HashMap<String, Crossing> crossings = new HashMap<>();
    private final HashMap<String, Road> roads = new HashMap<>();
    private Simulation simulation;
    private RoadNetwork roadNetwork;

    public ParticipantsController() {
        navigation = new Navigation();
        simulation = new Simulation(navigation);
    }

    @Override
    @PostMapping(ParticipantsInterface.SEND_CAR)
    public synchronized void sendCar(@RequestBody CarPath request) {
        simulation.addParticipant(new Participant(new Position(roads.get(request.getStartRoadSegmentId()), request.getStartRoadPosition()),
                new Position(roads.get(request.getDestinationRoadSegmentId()), request.getDestinationRoadPosition()), navigation));
    }

    @Override
    @PostMapping(ParticipantsInterface.NOTIFY_TRAFFIC_LIGHT_CHANGED)
    public void notifyTrafficLightChanged(@RequestBody TrafficLightChange change) {

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initNavigation();
        simulation.addParticipant(new Participant(new Position(roadNetwork.roads[0], 0), new Position(roadNetwork.roads[5], 1), navigation));
        simulation.addParticipant(new Participant(new Position(roadNetwork.roads[0], 300), new Position(roadNetwork.roads[5], 1), navigation));
        Thread t = new Thread(simulation);
        t.start();
    }


    private void initNavigation() {
        at.jku.softengws20.group1.shared.controlsystem.RoadNetwork roadNetworkSource = controlSystemService.getRoadNetwork();
        for (at.jku.softengws20.group1.shared.controlsystem.Crossing crossingSource : roadNetworkSource.getCrossings()) {
            at.jku.softengws20.group1.shared.controlsystem.Position positionNode = crossingSource.getPosition();
            String id = crossingSource.getId();
            crossings.put(id, new Crossing(id, new Coordinate(positionNode.getX(), positionNode.getY())));
        }
        for (RoadSegment roadSource : roadNetworkSource.getRoadSegments()) {
            roads.put(roadSource.getId(), new Road(roadSource.getId(), crossings.get(roadSource.getCrossingAId()), crossings.get(roadSource.getCrossingBId()),
                    roadSource.getLength() * 1000, roadSource.getDefaultSpeedLimit() / 3.6));
            new Road(roadSource.getId(), crossings.get(roadSource.getCrossingBId()), crossings.get(roadSource.getCrossingAId()),
                    roadSource.getLength() * 1000, roadSource.getDefaultSpeedLimit() / 3.6);
        }
        navigation.setRoadNetwork(roadNetwork = new RoadNetwork(crossings.values().toArray(Crossing[]::new), roads.values().toArray(Road[]::new)));
    }
}
