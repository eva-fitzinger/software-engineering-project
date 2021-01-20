package at.jku.softengws20.group1.maintenance.impl;

import at.jku.softengws20.group1.shared.impl.model.*;
import java.util.Date;

/**
 * A regular repair is a repair which is scheduled. It can have multiple timeslots which are sent to
 * the ControlSystem for approval (Implementation in Scheduling System).
 */

public class RegularRepair extends Repair {
    int priority;

    Timeslot[] timeslot;

    public RegularRepair(String repairId, RepairType repairType, String location, int priority, long duration,
                         int nrVehiclesNeeded, int nrWorkersNeeded) {
        super(repairId, repairType, location, duration, nrVehiclesNeeded, nrWorkersNeeded);
        this.priority = priority;
        this.timeslot = new Timeslot[0];
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Timeslot[] getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(Timeslot[] timeslot) {
        this.timeslot = timeslot;
    }
}
