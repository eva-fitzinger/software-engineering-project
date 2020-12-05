package at.jku.softengws20.group1.interfaces.impl.model;

public class TrafficLightRule implements at.jku.softengws20.group1.interfaces.detection.TrafficLightRule {

    private String crossingId;
    private String incomingRoadSegmentId;
    private double priority;

    public TrafficLightRule() {}

    public TrafficLightRule(String crossingId, String incomingRoadSegmentId, double priority) {
        this.crossingId = crossingId;
        this.incomingRoadSegmentId = incomingRoadSegmentId;
        this.priority = priority;
    }

    @Override
    public String getCrossingId() {
        return crossingId;
    }

    @Override
    public String getIncomingRoadSegmentId() {
        return incomingRoadSegmentId;
    }

    @Override
    public double getPriority() {
        return priority;
    }
}
