package at.jku.softengws20.group1.shared.impl.model;

public class TrafficLightChange implements at.jku.softengws20.group1.shared.detection.TrafficLightChange {
    private String crossingId;
    private String[] greenForRoads;

    public TrafficLightChange() {
    }

    public TrafficLightChange(String crossingId, String[] greenForRoads) {
        this.crossingId = crossingId;
        this.greenForRoads = greenForRoads;
    }


    @Override
    public String getCrossingId() {
        return crossingId;
    }

    @Override
    public String[] getGreenForRoads() {
        return greenForRoads;
    }
}
