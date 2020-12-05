package at.jku.softengws20.group1.interfaces.impl.model;

public class RoadSegment <T0 extends Position> implements at.jku.softengws20.group1.interfaces.controlsystem.RoadSegment {

    private String id;
    private String roadId;
    private String crossingAId;
    private String crossingBId;
    private RoadType roadType;
    private double length;
    private int defaultSpeedLimit;
    private T0[] path;

    public RoadSegment() {}

    public RoadSegment(String id, String roadId, String crossingAId, String crossingBId, RoadType roadType, double length, int defaultSpeedLimit, T0[] path) {
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
    public T0[] getPath() {
        return path;
    }
}
