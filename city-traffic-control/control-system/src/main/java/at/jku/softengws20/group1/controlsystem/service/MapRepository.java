package at.jku.softengws20.group1.controlsystem.service;

import at.jku.softengws20.group1.shared.impl.model.*;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;

@Repository
public class MapRepository {

    private RoadNetwork roadNetwork;

    @PostConstruct
    public void loadMap() {
        Collection<Road> roads = Arrays.asList(
                new Road("ring", "Ring", "R1"),
                new Road("diag", "Diagonal", "D2"),
                new Road("horizontal", "Horizontal", "H3")
        );

        Collection<RoadSegment> roadSegments = Arrays.asList(
                new RoadSegment<>("rs1", "ring", "1", "2", RoadType.RESIDENTIAL,
                        2, 50, new Position[0]),
                new RoadSegment<>("rs2", "horizontal", "2", "3", RoadType.RESIDENTIAL,
                        2, 50, new Position[0]),
                new RoadSegment<>("rs3", "ring", "4", "5", RoadType.RESIDENTIAL,
                        2, 50, new Position[0]),
                new RoadSegment<>("rs4", "ring", "2", "5", RoadType.RESIDENTIAL,
                        2, 50, new Position[0]),
                new RoadSegment<>("rs5", "diag", "1", "4", RoadType.RESIDENTIAL,
                        2, 50, new Position[0]),
                new RoadSegment<>("rs6", "ring", "3", "4", RoadType.RESIDENTIAL,
                        2, 50, new Position[0]),
                new RoadSegment<>("rs7", "ring", "1", "3", RoadType.RESIDENTIAL,
                        2, 50, new Position[0])
        );

        Collection<Crossing> crossings = Arrays.asList(
                new Crossing<>("1", new Position(4, 2), Arrays.asList("rs1", "rs7")),
                new Crossing<>("2", new Position(6, 4), Arrays.asList("rs1", "rs2", "rs4")),
                new Crossing<>("3", new Position(2, 4), Arrays.asList("rs2", "rs6", "rs7")),
                new Crossing<>("4", new Position(3, 6), Arrays.asList("rs3", "rs5", "rs6")),
                new Crossing<>("5", new Position(5, 6), Arrays.asList("rs3", "rs4")));

        roadNetwork = new RoadNetwork<>(crossings, roadSegments, roads, "A-B");
    }

    public RoadNetwork getRoadNetwork() {
        return roadNetwork;
    }
}
