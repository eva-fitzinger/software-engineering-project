package at.jku.softengws20.group1.maintenance.impl;

import at.jku.softengws20.group1.shared.impl.model.*;

import java.util.Date;

public class RegularRepair extends Repair {
    int priority;
    boolean approved;

    Timeslot[] timeslot;
    Date timeFrom;
    Date timeTo;

    public RegularRepair(String repairId, RepairType repairType, String location, int priority, long duration,
                         int nrVehiclesNeeded, int nrWorkersNeeded) {
        super(repairId, repairType, location, duration, nrVehiclesNeeded, nrWorkersNeeded);
        this.priority = priority;
        this.timeslot = new Timeslot[0];
    }
    public RegularRepair(String repairId, RepairType repairType, String location, int priority, long duration,
                         int nrVehiclesNeeded, int nrWorkersNeeded, Date from, Date to) {
        super(repairId, repairType, location, duration, nrVehiclesNeeded, nrWorkersNeeded);
        this.priority = priority;
        this.timeFrom = from;
        this.timeTo = to;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Timeslot[] getTimeslot() {
        return timeslot;
    }

    public void setTimeslot(Timeslot[] timeslot) {
        this.timeslot = timeslot;
    }

    public Date getTimeFrom() {
        return timeFrom;
    }

    public Date getTimeTo() {
        return timeTo;
    }
}
