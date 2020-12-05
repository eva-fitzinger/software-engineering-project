package controlsystem;

public interface RoadSegmentStatus {
    String getRoadSegmentId();
    boolean isOpen();
    double getTrafficLoad();
}
