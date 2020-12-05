package at.jku.softengws20.group1.interfaces.impl.model;

public class TrafficLoad implements at.jku.softengws20.group1.interfaces.detection.TrafficLoad {

    private String roadSegmentId;
    private String crossingId;
    private int carsWaiting;

    public TrafficLoad() {}

    public TrafficLoad(String roadSegmentId, String crossingId, int carsWaiting) {
        this.roadSegmentId = roadSegmentId;
        this.crossingId = crossingId;
        this.carsWaiting = carsWaiting;
    }

    @Override
    public String getRoadSegmentId() {
        return roadSegmentId;
    }

    @Override
    public String getCrossingId() {
        return crossingId;
    }

    @Override
    public int getCarsWaiting() {
        return carsWaiting;
    }
}
