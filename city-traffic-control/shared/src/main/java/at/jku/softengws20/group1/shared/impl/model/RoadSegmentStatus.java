package at.jku.softengws20.group1.shared.impl.model;

public class RoadSegmentStatus implements at.jku.softengws20.group1.shared.controlsystem.RoadSegmentStatus {

    private String roadSegmentId;
    private boolean isOpen;
    private double trafficLoad;

    public RoadSegmentStatus() {}

    public RoadSegmentStatus(String roadSegmentId, boolean isOpen, double trafficLoad) {
        this.roadSegmentId = roadSegmentId;
        this.isOpen = isOpen;
        this.trafficLoad = trafficLoad;
    }

    @Override
    public String getRoadSegmentId() {
        return roadSegmentId;
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public double getTrafficLoad() {
        return trafficLoad;
    }
}
