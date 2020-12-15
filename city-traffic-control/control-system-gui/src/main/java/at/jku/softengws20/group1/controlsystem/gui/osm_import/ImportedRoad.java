package at.jku.softengws20.group1.controlsystem.gui.osm_import;

import java.util.LinkedList;
import java.util.List;

class ImportedRoad {
    private String id;
    private String number;
    private String name;
    private List<ImportedRoadSegment> roadSegments = new LinkedList<>();

    String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    String getNumber() {
        return number;
    }

    void setNumber(String number) {
        this.number = number;
    }

    String getName() {
        return name;
    }

    String safeGetName() {
        if (name == null) {
            return "";
        }
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    List<ImportedRoadSegment> getRoadSegments() {
        return roadSegments;
    }

    void setRoadSegments(List<ImportedRoadSegment> roadSegments) {
        this.roadSegments = roadSegments;
    }
}
