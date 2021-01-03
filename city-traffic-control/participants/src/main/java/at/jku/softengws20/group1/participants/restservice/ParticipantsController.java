package at.jku.softengws20.group1.participants.restservice;

import at.jku.softengws20.group1.participants.navigation.Navigation;
import at.jku.softengws20.group1.participants.roadNetwork.*;
import at.jku.softengws20.group1.participants.simulation.Participant;
import at.jku.softengws20.group1.participants.simulation.Simulation;
import at.jku.softengws20.group1.shared.Config;
import at.jku.softengws20.group1.shared.controlsystem.RoadSegment;
import at.jku.softengws20.group1.shared.impl.model.RoadSegmentStatus;
import at.jku.softengws20.group1.shared.impl.model.TrafficLightChange;
import at.jku.softengws20.group1.shared.maintenance.CarPath;
import at.jku.softengws20.group1.shared.participants.ParticipantsInterface;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

@RestController
@RequestMapping(ParticipantsInterface.URL)
public class ParticipantsController implements ParticipantsInterface, ApplicationListener<ContextRefreshedEvent> {
    private final ParticipantsControlSystemService controlSystemService = new ParticipantsControlSystemService();
    private final Navigation navigation;
    private final HashMap<String, Crossing> crossings = new HashMap<>();
    private final HashMap<String, Road> roads = new HashMap<>();
    private Simulation simulation;
    private RoadNetwork roadNetwork;
    private Random random = new Random();

    public ParticipantsController() {
        navigation = new Navigation();
        simulation = new Simulation(navigation);
    }

    @Override
    @PostMapping(ParticipantsInterface.SEND_CAR)
    public synchronized void sendCar(@RequestBody CarPath request) {
        simulation.addParticipant(new Participant(new Position(roads.get(request.getStartRoadSegmentId()), request.getStartRoadPosition()),
                new Position(roads.get(request.getDestinationRoadSegmentId()), request.getDestinationRoadPosition()), navigation, request.getCallbackUri()));
    }

    @RequestMapping("participantPositions")
    public String participantPositions() {
        double minX = roadNetwork.getMinX();
        double minY = roadNetwork.getMinY();
        double maxX = roadNetwork.getMaxX();
        double maxY = roadNetwork.getMaxY();
        StringBuilder res = new StringBuilder("");
        for (Participant participant : simulation.getParticipants()) {
            Coordinate c = participant.getPosition().getCoordinate();
            Coordinate screenOffset = participant.getPosition().getRoad().getScreenOffset();
            res.append("<circle cx=").append((c.getX() - minX) / (maxX - minX) * 90 + 5 + screenOffset.getX())
                    .append("% cy=").append((c.getY() - minY) / (maxY - minY) * 90 + 5 + screenOffset.getY()).append("% r=2").append(" fill='")
                    .append(participant.getAcceleration() >= -0.1 && participant.getVelocity() > 0 ? "green" : "red").append("'/>");
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
            Coordinate screenOffset = road.getScreenOffset();
            gui.append("<line x1=").append(x1 + screenOffset.getX()).append("% y1=").append(y1 + screenOffset.getY())
                    .append("% x2=").append(x2 + screenOffset.getX()).append("% y2=").append(y2 + screenOffset.getY())
                    .append("% style='stroke:rgb(0,0,0);stroke-width:1'/>");
            //gui.append("<text x=").append((x1 + x2) / 2 + 1).append("% y=").append((y1 + y2) / 2 - 1).append("% class='small'>").append(road.getId()).append("</text>");
        }
        for (Crossing crossing : roadNetwork.crossings) {
            double x = (crossing.getPosition().getX() - minX) / (maxX - minX) * 90 + 5;
            double y = (crossing.getPosition().getY() - minY) / (maxY - minY) * 90 + 5;
            //gui.append("<text x=").append(x + 1).append("% y=").append(y - 1).append("% class='small'>").append(crossing.getId()).append("</text>");
            gui.append("<circle cx=").append(x).append("% cy=").append(y).append("% r=2").append(" fill='black'/>");
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
                "setInterval(refresh, 100);" +
                "</script>");
        return gui.toString();
    }

    @Override
    @PostMapping(ParticipantsInterface.NOTIFY_TRAFFIC_LIGHT_CHANGED)
    public void notifyTrafficLightChanged(@RequestBody TrafficLightChange change) {
        Crossing crossing = crossings.get(change.getCrossingId());
        crossing.getGreenRoads().clear();
        for (String roadID : change.getGreenForRoads()) {
            crossing.getGreenRoads().add(roads.get(roadID));
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        initNavigation();
        Thread t = new Thread(simulation);
        t.setName("simulator");
        t.setPriority(1);
        t.start();
        Timer pollStatusTimer = new Timer(true);
        pollStatusTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                pollRoadNetworkState();
            }
        }, 0, (long)(60 * 1000 / Config.REAL_TIME_FACTOR));
    }

    public void pollRoadNetworkState() {
        RoadSegmentStatus[] status = controlSystemService.getStatus();
        for (RoadSegmentStatus s : status) {
            Road road = roads.get(s.getRoadSegmentId());
            road.setClosed(!s.isOpen());
            road.setEstimatedSpeed(Math.min(road.getSpeedLimit(), Math.max(3, road.getSpeedLimit() * (1 - s.getTrafficLoad()))));
        }
        roadNetwork.incVersion();
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
                    roadSource.getLength() * 1000, Math.max(roadSource.getDefaultSpeedLimit(), 30) / 3.6));
        }
        navigation.setRoadNetwork(roadNetwork = new RoadNetwork(crossings.values().toArray(Crossing[]::new), roads.values().toArray(Road[]::new)));
    }
}
