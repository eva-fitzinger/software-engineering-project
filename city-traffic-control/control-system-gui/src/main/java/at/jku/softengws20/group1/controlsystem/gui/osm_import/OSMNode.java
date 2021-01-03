package at.jku.softengws20.group1.controlsystem.gui.osm_import;

import java.util.HashMap;
import java.util.Map;

class OSMNode {
    private String id;
    private double lat;
    private double lon;
    private Map<String, OSMWay> streets = new HashMap<>();

    boolean isCrossing() {
        return streets.size() > 1;
    }

    String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    double getLat() {
        return lat;
    }

    void setLat(double lat) {
        this.lat = lat;
    }

    double getLon() {
        return lon;
    }

    void setLon(double lon) {
        this.lon = lon;
    }

    Map<String, OSMWay> getStreets() {
        return streets;
    }
}
