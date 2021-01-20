package at.jku.softengws20.group1.maintenance.impl;

import java.util.Date;

/**
 * EmergencyRepairs are Repairs that occur and have to be dealt with right away.
 */
public class EmergencyRepair extends Repair {
    private final int gravity;
    private final Date from;
    private final Date to;

    public EmergencyRepair(String repairId, RepairType repairType, String location, int gravity,
                           int nrVehiclesNeeded, int nrWorkersNeeded, Date from, long duration) {
        super(repairId, repairType, location, -1, nrVehiclesNeeded, nrWorkersNeeded);
        this.gravity = gravity;
        this.from = from;
        this.to = new Date(from.getTime() + duration);
    }

    public int getGravity() {
        return gravity;
    }

    @Override
    public Date getFrom() {
        return from;
    }

    @Override
    public Date getTo() {
        return to;
    }
}
