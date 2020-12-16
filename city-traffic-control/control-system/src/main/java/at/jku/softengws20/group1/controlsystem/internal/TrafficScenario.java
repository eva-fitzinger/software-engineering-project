package at.jku.softengws20.group1.controlsystem.internal;

import at.jku.softengws20.group1.shared.impl.model.TrafficLightRule;

import java.util.ArrayList;

public class TrafficScenario {
    String id;
    ArrayList<TrafficLightRule> trafficLightRules = new ArrayList<TrafficLightRule>();

    public ArrayList<TrafficLightRule> getTrafficLightRules() {
        return trafficLightRules;
    }

    public TrafficScenario (String id) {
        this.id = id;
    }

    public void addRule(TrafficLightRule trafficLightRule) {
        trafficLightRules.add(trafficLightRule);
    }

}
