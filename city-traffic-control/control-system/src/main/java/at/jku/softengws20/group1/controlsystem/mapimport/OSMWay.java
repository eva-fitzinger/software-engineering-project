package at.jku.softengws20.group1.controlsystem.mapimport;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class OSMWay {
    private String id;
    private List<OSMNode> nodes = new LinkedList<>();
    private Map<String, String> tags = new HashMap<>();

    private static final String TAG_NAME = "name";
    private static final String TAG_HIGHWAY = "highway";
    private static final String TAG_ROAD_REF = "ref";

    public String getName() {
        return tags.getOrDefault(TAG_NAME, null);
    }

    public String getRoadType() {
        return tags.getOrDefault(TAG_HIGHWAY, null);
    }

    public String getRoadRef() {
        return tags.getOrDefault(TAG_ROAD_REF, null);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<OSMNode> getNodes() {
        return nodes;
    }

    public Map<String, String> getTags() {
        return tags;
    }
}
