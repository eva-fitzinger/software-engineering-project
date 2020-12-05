package detection;

public interface TrafficLightRule {
    String getCrossingId();
    String getIncomingRoadSegmentId();
    double getPriority();
}
