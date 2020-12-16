package at.jku.softengws20.group1.controlsystem.service;



import at.jku.softengws20.group1.shared.detection.TrafficLightRule;

import java.util.ArrayList;
import java.util.HashMap;

public class TrafficLightRuleRepository {
    private ArrayList<TrafficLightRule> trafficLightRules = new ArrayList<TrafficLightRule>();

    public void pushTrafficListRule(TrafficLightRule trafficLightRule) {
        for (TrafficLightRule t:trafficLightRules) {
            if (t.getCrossingId() == trafficLightRule.getCrossingId() && t.getIncomingRoadSegmentId() == trafficLightRule.getIncomingRoadSegmentId()) {

            }
        }
    }

}
