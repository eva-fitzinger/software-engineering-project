package at.jku.softengws20.group1.shared.impl.model;

import at.jku.softengws20.group1.shared.detection.TrafficLightState;

public class TrafficLightChange implements at.jku.softengws20.group1.shared.detection.TrafficLightChange {
    private final String crossingId;
    private TrafficLightState trafficLightState;

    public TrafficLightChange(String crossingId, TrafficLightState trafficLightState) {
        this.crossingId = crossingId;
        this.trafficLightState = trafficLightState;
    }


    @Override
    public String getCrossingId() {
        return crossingId;
    }

    @Override
    public TrafficLightState getTrafficLightState() {
        return trafficLightState;
    }
}
