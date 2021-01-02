package at.jku.softengws20.group1.controlsystem.gui.model;

import at.jku.softengws20.group1.shared.impl.model.TrafficLightRule;

public class TrafficScenarioModel {
    private String name;
    private TrafficLightRule[] trafficLightRules;
    private ScheduleModel schedule;
    private boolean defaultScenario;

    public String getName() {
        return name;
    }

    public TrafficLightRule[] getTrafficLightRules() {
        return trafficLightRules;
    }

    public ScheduleModel getSchedule() {
        return schedule;
    }

    public boolean isDefaultScenario() {
        return defaultScenario;
    }
}
