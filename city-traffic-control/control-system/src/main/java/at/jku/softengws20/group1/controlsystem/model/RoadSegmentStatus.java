package at.jku.softengws20.group1.controlsystem.model;

public class RoadSegmentStatus implements at.jku.softengws20.group1.interfaces.controlsystem.RoadSegmentStatus {

    private String roadSegmentId;
    private boolean isOpen;
    private double trafficLoad;

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
