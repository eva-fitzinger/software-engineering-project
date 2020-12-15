package at.jku.softengws20.group1.controlsystem.gui.osm_import;

import at.jku.softengws20.group1.shared.impl.model.Position;
import at.jku.softengws20.group1.shared.impl.model.RoadType;

import java.util.LinkedList;
import java.util.List;

class ImportedRoadSegment {
    private String id;
    private ImportedRoad road;
    private ImportedCrossing crossingA;
    private ImportedCrossing crossingB;
    private RoadType roadType;
    private int speedLimit;
    private List<Position> path = new LinkedList<>();

    String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    ImportedRoad getRoad() {
        return road;
    }

    void setRoad(ImportedRoad road) {
        this.road = road;
    }

    ImportedCrossing getCrossingA() {
        return crossingA;
    }

    void setCrossingA(ImportedCrossing crossingA) {
        this.crossingA = crossingA;
    }

    ImportedCrossing getCrossingB() {
        return crossingB;
    }

    void setCrossingB(ImportedCrossing crossingB) {
        this.crossingB = crossingB;
    }

    List<Position> getPath() {
        return path;
    }

    RoadType getRoadType() {
        return roadType;
    }

    void setRoadType(RoadType roadType) {
        this.roadType = roadType;
    }

    int getSpeedLimit() {
        return speedLimit;
    }

    void setSpeedLimit(int speedLimit) {
        this.speedLimit = speedLimit;
    }

    double calculateLength() {
        double length = 0;
        Position from = null;
        if (crossingA != null) {
            from = crossingA.getPosition();
        }
        for (var p : path) {
            if (from == null) {
                from = p;
                continue;
            }
            length += getDist(from, p);
            from = p;
        }
        if (crossingB != null) {
            if (from != null) {
                length += getDist(from, crossingB.getPosition());
            }
        }
        return length;
    }

    private static double getDist(Position p1, Position p2) {
        double x = Math.abs(p2.getX() - p1.getX());
        double y = Math.abs(p2.getY() - p1.getY());
        return Math.sqrt(x * x + y * y);
    }
}
