package at.jku.softengws20.group1.controlsystem.model;

import at.jku.softengws20.group1.interfaces.controlsystem.Position;
import at.jku.softengws20.group1.interfaces.RoadType;

public class RoadSegment implements at.jku.softengws20.group1.interfaces.controlsystem.RoadSegment {

    private String id;
    private String roadId;
    private String crossingAId;
    private String crossingBId;
    private RoadType roadType;
    private double length;
    private int defaultSpeedLimit;
    private Position[] path;

    public RoadSegment(String id, String roadId, String crossingAId, String crossingBId, RoadType roadType, double length, int defaultSpeedLimit, Position[] path) {
        this.id = id;
        this.roadId = roadId;
        this.crossingAId = crossingAId;
        this.crossingBId = crossingBId;
        this.roadType = roadType;
        this.length = length;
        this.defaultSpeedLimit = defaultSpeedLimit;
        this.path = path;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getRoadId() {
        return roadId;
    }

    @Override
    public String getRoadType() {
        return roadType.name();
    }

    @Override
    public int getDefaultSpeedLimit() {
        return defaultSpeedLimit;
    }

    @Override
    public double getLength() {
        return length;
    }

    @Override
    public String getCrossingAId() {
        return crossingAId;
    }

    @Override
    public String getCrossingBId() {
        return crossingBId;
    }

    @Override
    public Position[] getPath() {
        return path;
    }
}
