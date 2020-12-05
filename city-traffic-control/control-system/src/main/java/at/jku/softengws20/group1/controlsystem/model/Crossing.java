package at.jku.softengws20.group1.controlsystem.model;

public class Crossing implements at.jku.softengws20.group1.interfaces.controlsystem.Crossing {
    private String id;
    private Position position;
    private String[] roadSegmentIds;

    public Crossing(String id, Position position, String[] roadSegmentIds) {
        this.id = id;
        this.position = position;
        this.roadSegmentIds = roadSegmentIds;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public double getX() {
        return position.getX();
    }

    @Override
    public double getY() {
        return position.getY();
    }

    @Override
    public String[] getRoadSegmentIds() {
        return roadSegmentIds;
    }
}
