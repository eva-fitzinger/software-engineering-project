package at.jku.softengws20.group1.shared.detection;

public interface TrafficLightRule {
    String getCrossingId();
    String getIncomingRoadSegmentId();
    double getPriority();
}
