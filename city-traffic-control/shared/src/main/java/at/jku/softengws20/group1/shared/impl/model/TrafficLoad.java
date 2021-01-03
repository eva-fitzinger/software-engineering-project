package at.jku.softengws20.group1.shared.impl.model;

public class TrafficLoad implements at.jku.softengws20.group1.shared.detection.TrafficLoad {

    private String roadSegmentId;
    private int carsWaiting;

    public TrafficLoad() {}

    public TrafficLoad(String roadSegmentId, int carsWaiting) {
        this.roadSegmentId = roadSegmentId;
        this.carsWaiting = carsWaiting;
    }

    @Override
    public String getRoadSegmentId() {
        return roadSegmentId;
    }

    @Override
    public int getCarsWaiting() {
        return carsWaiting;
    }
}
