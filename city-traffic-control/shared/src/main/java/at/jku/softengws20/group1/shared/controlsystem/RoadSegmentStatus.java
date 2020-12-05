package at.jku.softengws20.group1.shared.controlsystem;

public interface RoadSegmentStatus {
    String getRoadSegmentId();
    boolean isOpen();
    double getTrafficLoad();
}
