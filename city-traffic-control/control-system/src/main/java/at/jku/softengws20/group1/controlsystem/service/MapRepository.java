package at.jku.softengws20.group1.controlsystem.service;

import at.jku.softengws20.group1.shared.impl.model.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Repository
public class MapRepository {

    private RoadNetwork roadNetwork;

    private void loadDummyMap() {
        Road[] roads = new Road[]{
                new Road("ring", "Ring", "R1"),
                new Road("diag", "Diagonal", "D2"),
                new Road("horizontal", "Horizontal", "H3")
        };

        RoadSegment[] roadSegments = new RoadSegment[]{
                new RoadSegment("rs1", "ring", "1", "2", RoadType.RESIDENTIAL,
                        2, 50, new Position[0]),
                new RoadSegment("rs2", "horizontal", "2", "3", RoadType.RESIDENTIAL,
                        2, 50, new Position[0]),
                new RoadSegment("rs3", "ring", "4", "5", RoadType.RESIDENTIAL,
                        2, 50, new Position[0]),
                new RoadSegment("rs4", "ring", "2", "5", RoadType.RESIDENTIAL,
                        2, 50, new Position[0]),
                new RoadSegment("rs5", "diag", "1", "4", RoadType.PRIMARY,
                        2, 50, new Position[0]),
                new RoadSegment("rs6", "ring", "3", "4", RoadType.RESIDENTIAL,
                        2, 50, new Position[0]),
                new RoadSegment("rs7", "ring", "1", "3", RoadType.RESIDENTIAL,
                        2, 50, new Position[0]),
                new RoadSegment("rs8", "ring", "3", "1", RoadType.RESIDENTIAL,
                        3, 70, new Position[0])

        };

        Crossing[] crossings = new Crossing[]{
                new Crossing("1", new Position(4, 2), new String[]{"rs1", "rs7"}),
                new Crossing("2", new Position(6, 4), new String[]{"rs1", "rs2", "rs4"}),
                new Crossing("3", new Position(2, 4), new String[]{"rs2", "rs6", "rs7"}),
                new Crossing("4", new Position(3, 6), new String[]{"rs3", "rs5", "rs6"}),
                new Crossing("5", new Position(5, 6), new String[]{"rs3", "rs4"}),
        };

        roadNetwork = new RoadNetwork(crossings, roadSegments, roads, "A-B");
    }

    private void loadJsonMap() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.getFactory().configure(JsonWriteFeature.ESCAPE_NON_ASCII.mappedFeature(), true);
        try (JsonParser parser = mapper.createParser(getClass().getClassLoader().getResourceAsStream("urfahr.json"))) {
            roadNetwork = parser.readValueAs(RoadNetwork.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void loadMap() {
        //loadJsonMap();
        loadDummyMap();
    }

    public RoadNetwork getRoadNetwork() {
        return roadNetwork;
    }
}
