package at.jku.softengws20.group1.shared.impl.model;

public class Crossing <T0 extends Position>
        implements at.jku.softengws20.group1.shared.controlsystem.Crossing {
    private String id;
    private T0 position;
    private String[] roadSegmentIds;

    public Crossing() {}

    public Crossing(String id, T0 position, String[] roadSegmentIds) {
        this.id = id;
        this.position = position;
        this.roadSegmentIds = roadSegmentIds;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public T0 getPosition() {
        return position;
    }

    @Override
    public String[] getRoadSegmentIds() {
        return roadSegmentIds;
    }
}
