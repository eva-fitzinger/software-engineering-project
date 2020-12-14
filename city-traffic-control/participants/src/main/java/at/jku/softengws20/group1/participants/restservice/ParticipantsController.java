package at.jku.softengws20.group1.participants.restservice;

import at.jku.softengws20.group1.participants.navigation.Navigation;
import at.jku.softengws20.group1.participants.roadNetwork.*;
import at.jku.softengws20.group1.shared.controlsystem.ControlSystemInterface;
import at.jku.softengws20.group1.shared.maintenance.MaintenanceCarDestination;
import at.jku.softengws20.group1.shared.participants.ParticipantsInterface;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping(ParticipantsInterface.URL)
public class ParticipantsController implements ParticipantsInterface, ApplicationListener<ContextRefreshedEvent> {
    final String uri = "http://localhost:8080";
    private final Navigation navigation;
    private RoadNetwork roadNetwork;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public ParticipantsController() {
        navigation = new Navigation();
    }

    @Override
    @PostMapping(ParticipantsInterface.SEND_MAINTENANCE_CAR_URL)
    public void sendMaintenanceCar(@RequestBody MaintenanceCarDestination request) {

    }

    @Override
    @PostMapping(ParticipantsInterface.NOTIFY_CARS_PASSED_URL)
    public void notifyCarsPassed(@RequestBody String[] carIds) {

    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initNavigation();
        Road[] path = navigation.getPath(new Position(roadNetwork.roads[0], 0), new Position(roadNetwork.roads[5], 0));
        int deb =0;
    }

    private void initNavigation() {
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(uri + ControlSystemInterface.URL + "/" + ControlSystemInterface.GET_ROAD_NETWORK_URL, String.class);
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode root = mapper.readTree(result);
            HashMap<String, Crossing> crossings = new HashMap<>();
            for (JsonNode crossingNode : root.get("crossings")) {
                JsonNode positionNode = crossingNode.get("position");
                String id = crossingNode.get("id").asText();
                crossings.put(id, new Crossing(id, new Coordinate(positionNode.get("x").asDouble(), positionNode.get("y").asDouble())));
            }
            ArrayList<Road> roads = new ArrayList<>();
            for (JsonNode roadNode : root.get("roadSegments")) {
                roads.add(new Road(roadNode.get("id").asText(), crossings.get(roadNode.get("crossingAId").asText()), crossings.get(roadNode.get("crossingBId").asText()),
                        roadNode.get("length").asDouble(), roadNode.get("defaultSpeedLimit").asDouble()));
            }
            navigation.setRoadNetwork(roadNetwork = new RoadNetwork(crossings.values().toArray(Crossing[]::new), roads.toArray(Road[]::new)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
