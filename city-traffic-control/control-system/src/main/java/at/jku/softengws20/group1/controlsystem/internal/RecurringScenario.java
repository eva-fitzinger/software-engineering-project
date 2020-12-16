package at.jku.softengws20.group1.controlsystem.internal;

import at.jku.softengws20.group1.shared.impl.model.Timeslot;

public class RecurringScenario extends TrafficScenario {


    private String name;
    private Schedule schedule;
    private Boolean defaultScenario;

    public RecurringScenario(String id, String name, Schedule schedule, Boolean defaultScenario) {
        super(id);
        this.name = name;
        this.schedule = schedule;
        this.defaultScenario = defaultScenario;
    }

    public String getName() {
        return name;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Boolean getDefaultScenario() {
        return defaultScenario;
    }


}
