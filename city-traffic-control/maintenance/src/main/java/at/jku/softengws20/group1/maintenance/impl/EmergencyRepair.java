package at.jku.softengws20.group1.maintenance.impl;

import java.util.Date;

public class EmergencyRepair extends Repair {// TODO: Milestone 3.2
    int gravity;
    private Date from;
    private Date to;

    public EmergencyRepair(String repairId, String location, int gravity, int nrVehiclesNeeded, int nrWorkersNeeded, Date from, long duration) {
        super(repairId, RepairType.EMERGENCY_REPAIR, location, -1, nrVehiclesNeeded, nrWorkersNeeded);
        this.gravity = gravity;
        this.from = from;
        this.to = new Date(from.getTime() + duration);
    }

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
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
