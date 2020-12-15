package at.jku.softengws20.group1.participants.restservice;

import at.jku.softengws20.group1.participants.navigation.Navigation;
import at.jku.softengws20.group1.participants.roadNetwork.*;
import at.jku.softengws20.group1.shared.controlsystem.RoadSegment;
import at.jku.softengws20.group1.shared.impl.model.TrafficLightChange;
import at.jku.softengws20.group1.shared.maintenance.CarPath;
import at.jku.softengws20.group1.shared.participants.ParticipantsInterface;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping(ParticipantsInterface.URL)
public class ParticipantsController implements ParticipantsInterface, ApplicationListener<ContextRefreshedEvent> {
    private final ParticipantsControlSystemService controlSystemService = new ParticipantsControlSystemService();
    private final Navigation navigation;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private RoadNetwork roadNetwork;

    public ParticipantsController() {
        navigation = new Navigation();
    }

    @Override
    @PostMapping(ParticipantsInterface.SEND_CAR)
    public void sendCar(@RequestBody CarPath request) {

    }

    @RequestMapping("gui")
    public String getGui() {
        double minX = Double.POSITIVE_INFINITY;
        double minY = Double.POSITIVE_INFINITY;
        double maxX = Double.NEGATIVE_INFINITY;
        double maxY = Double.NEGATIVE_INFINITY;
        for (Crossing crossing : roadNetwork.crossings) {
            if(crossing.getPosition().getX() < minX) minX = crossing.getPosition().getX();
            if(crossing.getPosition().getY() < minY) minY = crossing.getPosition().getY();
            if(crossing.getPosition().getX() > maxX) maxX = crossing.getPosition().getX();
            if(crossing.getPosition().getY() > maxY) maxY = crossing.getPosition().getY();
        }
        StringBuilder gui = new StringBuilder("<svg width=100% height=100%>\n");
        for (Road road : roadNetwork.roads) {
            double x1 = (road.getStart().getPosition().getX()-minX)/(maxX-minX)*90+5;
            double y1 = (road.getStart().getPosition().getY()-minY)/(maxY-minY)*90+5;
            double x2 = (road.getEnd().getPosition().getX()-minX)/(maxX-minX)*90+5;
            double y2 = (road.getEnd().getPosition().getY()-minY)/(maxY-minY)*90+5;
            gui.append("<line x1=").append(x1).append("% y1=").append(y1).append("% x2=").append(x2).append("% y2=").append(y2).append("% style='stroke:rgb(0,0,0);stroke-width:2'/>");
            gui.append("<text x=").append((x1+x2)/2+1).append("% y=").append((y1+y2)/2-1).append("% class='small'>").append(road.getId()).append("</text>");
        }
        for (Crossing crossing : roadNetwork.crossings) {
            double x = (crossing.getPosition().getX()-minX)/(maxX-minX)*90+5;
            double y = (crossing.getPosition().getY()-minY)/(maxY-minY)*90+5;
            gui.append("<text x=").append(x+1).append("% y=").append(y-1).append("% class='small'>").append(crossing.getId()).append("</text>");
            gui.append("<circle cx=").append(x).append("% cy=").append(y).append("% r=5").append(" fill='red'/>");
        }
        gui.append("</svg");
        return gui.toString();
    }

    @Override
    @PostMapping(ParticipantsInterface.NOTIFY_TRAFFIC_LIGHT_CHANGED)
    public void notifyTrafficLightChanged(@RequestBody TrafficLightChange change) {
        int deb = 0;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initNavigation();
        Road[] path = navigation.getPath(new Position(roadNetwork.roads[0], 0), new Position(roadNetwork.roads[5], 0));
        int deb = 0;
    }

    private void initNavigation() {
        at.jku.softengws20.group1.shared.controlsystem.RoadNetwork roadNetworkSource = controlSystemService.getRoadNetwork();
        HashMap<String, Crossing> crossings = new HashMap<>();
        for (at.jku.softengws20.group1.shared.controlsystem.Crossing crossingSource : roadNetworkSource.getCrossings()) {
            at.jku.softengws20.group1.shared.controlsystem.Position positionNode = crossingSource.getPosition();
            String id = crossingSource.getId();
            crossings.put(id, new Crossing(id, new Coordinate(positionNode.getX(), positionNode.getY())));
        }
        ArrayList<Road> roads = new ArrayList<>();
        for (RoadSegment roadSource : roadNetworkSource.getRoadSegments()) {
            roads.add(new Road(roadSource.getId(), crossings.get(roadSource.getCrossingAId()), crossings.get(roadSource.getCrossingBId()),
                    roadSource.getLength(), roadSource.getDefaultSpeedLimit()));
        }
        navigation.setRoadNetwork(roadNetwork = new RoadNetwork(crossings.values().toArray(Crossing[]::new), roads.toArray(Road[]::new)));
    }
}
