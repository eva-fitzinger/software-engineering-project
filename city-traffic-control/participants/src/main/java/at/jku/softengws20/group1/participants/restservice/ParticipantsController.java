package at.jku.softengws20.group1.participants.restservice;

import at.jku.softengws20.group1.participants.navigation.Navigation;
import at.jku.softengws20.group1.participants.roadNetwork.*;
import at.jku.softengws20.group1.participants.simulation.Participant;
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
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping(ParticipantsInterface.URL)
public class ParticipantsController implements ParticipantsInterface, ApplicationListener<ContextRefreshedEvent> {
    private final ParticipantsControlSystemService controlSystemService = new ParticipantsControlSystemService();
    private final Navigation navigation;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    private RoadNetwork roadNetwork;
    private Random random = new Random();

    public ParticipantsController() {
        navigation = new Navigation();
    }

    @Override
    @PostMapping(ParticipantsInterface.SEND_CAR)
    public void sendCar(@RequestBody CarPath request) {

    }

    @RequestMapping("participantPositions")
    public String participantPositions() {
        double minX = roadNetwork.getMinX();
        double minY = roadNetwork.getMinY();
        double maxX = roadNetwork.getMaxX();
        double maxY = roadNetwork.getMaxY();
        Road r = roadNetwork.roads[random.nextInt(roadNetwork.roads.length)];
        Participant[] p = new Participant[]{new Participant(new Position(roadNetwork.roads[random.nextInt(roadNetwork.roads.length)], random.nextDouble() % r.getLength()),
                new Position(r, r.getLength()))};
        StringBuilder res = new StringBuilder("");
        for (Participant participant : p) {
            Coordinate c = participant.getPosition().getCoordinate();
            res.append("<circle cx=").append((c.getX()-minX)/(maxX-minX)*90+5).append("% cy=").append((c.getY()-minY)/(maxY-minY)*90+5).append("% r=3").append(" fill='red'/>");
        }
        return res.toString();
    }

    @RequestMapping("gui")
    public String getGui() {
        double minX = roadNetwork.getMinX();
        double minY = roadNetwork.getMinY();
        double maxX = roadNetwork.getMaxX();
        double maxY = roadNetwork.getMaxY();
        StringBuilder gui = new StringBuilder("<svg width=100% height=100% style='position:absolute'>\n");
        for (Road road : roadNetwork.roads) {
            double x1 = (road.getStart().getPosition().getX() - minX) / (maxX - minX) * 90 + 5;
            double y1 = (road.getStart().getPosition().getY() - minY) / (maxY - minY) * 90 + 5;
            double x2 = (road.getEnd().getPosition().getX() - minX) / (maxX - minX) * 90 + 5;
            double y2 = (road.getEnd().getPosition().getY() - minY) / (maxY - minY) * 90 + 5;
            gui.append("<line x1=").append(x1).append("% y1=").append(y1).append("% x2=").append(x2).append("% y2=").append(y2).append("% style='stroke:rgb(0,0,0);" +
                    "stroke-width:2'/>");
            gui.append("<text x=").append((x1 + x2) / 2 + 1).append("% y=").append((y1 + y2) / 2 - 1).append("% class='small'>").append(road.getId()).append("</text>");
        }
        for (Crossing crossing : roadNetwork.crossings) {
            double x = (crossing.getPosition().getX() - minX) / (maxX - minX) * 90 + 5;
            double y = (crossing.getPosition().getY() - minY) / (maxY - minY) * 90 + 5;
            gui.append("<text x=").append(x + 1).append("% y=").append(y - 1).append("% class='small'>").append(crossing.getId()).append("</text>");
            gui.append("<circle cx=").append(x).append("% cy=").append(y).append("% r=5").append(" fill='black'/>");
        }
        gui.append("</svg>");
        gui.append("<svg id='participants' width=100% height=100% style='position:absolute'></svg>");
        gui.append("<script type='text/javascript'>" +
                "function refresh() {\n" +
                "  var xhttp = new XMLHttpRequest();\n" +
                "  xhttp.onreadystatechange = function() {\n" +
                "    if (this.readyState == 4 && this.status == 200) {\n" +
                "     document.getElementById(\"participants\").innerHTML = this.responseText;\n" +
                "    }\n" +
                "  };\n" +
                "  xhttp.open(\"GET\", \"/participants/participantPositions\", true);\n" +
                "  xhttp.send();\n" +
                "}\n" +
                "setInterval(refresh, 1000);" +
                "</script>");
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
