package at.jku.softengws20.group1.controlsystem.mapimport;

import java.util.HashMap;
import java.util.Map;

public class OSMStreetNetwork {
    private Map<String, OSMNode> nodes = new HashMap<>();
    private Map<String, OSMWay> ways = new HashMap<>();

    public Map<String, OSMNode> getNodes() {
        return nodes;
    }

    public Map<String, OSMWay> getWays() {
        return ways;
    }
}
