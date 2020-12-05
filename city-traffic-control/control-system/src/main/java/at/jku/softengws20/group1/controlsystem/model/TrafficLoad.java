package at.jku.softengws20.group1.controlsystem.model;

public class TrafficLoad implements at.jku.softengws20.group1.interfaces.detection.TrafficLoad {

    private String roadSegmentId;
    private String crossingId;
    private int carsWaiting;

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
