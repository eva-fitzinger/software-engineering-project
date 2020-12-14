package at.jku.softengws20.group1.participants.roadNetwork;

import java.util.ArrayList;

public class Crossing {
    private final String id;
    private final Coordinate position;
    private final ArrayList<Road> roads;

    public Crossing(String id, Coordinate position) {
        this.id = id;
        this.position = position;
        roads = new ArrayList<Road>();
    }

    protected void addRoad(Road road) {
        roads.add(road);
    }

    public String getId() {
        return id;
    }

    public Coordinate getPosition() {
        return position;
    }

    public Iterable<Road> getRoads() {
        return roads;
    }
}
