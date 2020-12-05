package at.jku.softengws20.group1.controlsystem.mapimport;

import java.util.HashMap;
import java.util.Map;

public class OSMNode {
    private String id;
    private double lat;
    private double lon;
    private Map<String, OSMWay> streets = new HashMap<>();

    public boolean isCrossing() {
        return streets.size() > 1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public Map<String, OSMWay> getStreets() {
        return streets;
    }
}
