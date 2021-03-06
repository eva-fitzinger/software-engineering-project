package at.jku.softengws20.group1.controlsystem.gui.osm_import;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class OSMWay {
    private String id;
    private List<OSMNode> nodes = new LinkedList<>();
    private Map<String, String> tags = new HashMap<>();

    private static final String TAG_NAME = "name";
    private static final String TAG_HIGHWAY = "highway";
    private static final String TAG_ROAD_REF = "ref";
    private static final String TAG_SPEED_LIMIT = "maxspeed";
    private static final String TAG_ONEWAY = "oneway";
    private static final String TAG_LANES = "lanes";
    private static final String TAG_LANES_FORWARD = "lanes:forward";
    private static final String TAG_LANES_BACKWARD = "lanes:backward";

    String getName() {
        return tags.getOrDefault(TAG_NAME, null);
    }

    String getRoadType() {
        return tags.getOrDefault(TAG_HIGHWAY, null);
    }

    String getRoadRef() {
        return tags.getOrDefault(TAG_ROAD_REF, null);
    }

    int getSpeedLimit() {
        return Integer.parseInt(tags.getOrDefault(TAG_SPEED_LIMIT, "0"));
    }

    boolean isOneWay() {
        return tags.getOrDefault(TAG_ONEWAY, "").equals("yes");
    }

    int getForwardLaneCount() {
        int lc = Integer.parseInt(tags.getOrDefault(TAG_LANES, "1"));
        if (isOneWay()) {
            return lc;
        }
        return Integer.parseInt(tags.getOrDefault(TAG_LANES_FORWARD, Integer.toString(Math.max(1, lc / 2))));
    }

    int getBackwardLaneCount() {
        if (isOneWay()) {
            return 0;
        }
        int lc = Integer.parseInt(tags.getOrDefault(TAG_LANES, "1"));
        return Integer.parseInt(tags.getOrDefault(TAG_LANES_BACKWARD, Integer.toString(Math.max(1, lc / 2))));
    }

    String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    List<OSMNode> getNodes() {
        return nodes;
    }

    Map<String, String> getTags() {
        return tags;
    }

    void merge(OSMWay other) {
        // remove turn lanes
        if (getForwardLaneCount() + getBackwardLaneCount() > other.getForwardLaneCount() + other.getBackwardLaneCount()) {
            tags = other.tags;
        }
    }
}
