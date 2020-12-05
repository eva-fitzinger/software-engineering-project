package at.jku.softengws20.group1.interfaces.detection;

public interface TrafficLoad {
    String getRoadSegmentId();
    String getCrossingId();
    int getCarsWaiting();
}
