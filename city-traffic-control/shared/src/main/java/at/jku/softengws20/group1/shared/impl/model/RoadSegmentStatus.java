package at.jku.softengws20.group1.shared.impl.model;

public class RoadSegmentStatus implements at.jku.softengws20.group1.shared.controlsystem.RoadSegmentStatus {

    private final double DEFAULT_TRAFFICLOAD = 0.0;
    private final boolean DEFAULT_OPEN = true;

    private String roadSegmentId;
    private boolean isOpen;
    private double trafficLoad;

    public RoadSegmentStatus() {}

    public RoadSegmentStatus(String roadSegmentId, boolean isOpen, double trafficLoad) {
        this.roadSegmentId = roadSegmentId;
        this.isOpen = isOpen;
        this.trafficLoad = trafficLoad;
    }

    public RoadSegmentStatus(String roadSegmentId, boolean isOpen) {
        this.roadSegmentId = roadSegmentId;
        this.isOpen = isOpen;
        this.trafficLoad = DEFAULT_TRAFFICLOAD;
    }

    public RoadSegmentStatus(String roadSegmentId, double trafficLoad) {
        this.roadSegmentId = roadSegmentId;
        this.isOpen = DEFAULT_OPEN;
        this.trafficLoad = trafficLoad;
    }

    public RoadSegmentStatus(String roadSegmentId) {
        this.roadSegmentId = roadSegmentId;
        this.isOpen = DEFAULT_OPEN;
        this.trafficLoad = DEFAULT_TRAFFICLOAD;
    }

    @Override
    public String getRoadSegmentId() {
        return roadSegmentId;
    }

    @Override
    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public void close() { isOpen=false; }

    public void open() { isOpen=true; }

    @Override
    public double getTrafficLoad() {
        return trafficLoad;
    }

    public void setTrafficLoad(double trafficLoad) { this.trafficLoad = trafficLoad; }
}
