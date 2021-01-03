package at.jku.softengws20.group1.controlsystem.gui.osm_import;

import java.util.HashMap;
import java.util.Map;

class OSMStreetNetwork {
    private Map<String, OSMNode> nodes = new HashMap<>();
    private Map<String, OSMWay> ways = new HashMap<>();

    Map<String, OSMNode> getNodes() {
        return nodes;
    }

    Map<String, OSMWay> getWays() {
        return ways;
    }
}
