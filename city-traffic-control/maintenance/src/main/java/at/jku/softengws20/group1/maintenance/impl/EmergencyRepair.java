package at.jku.softengws20.group1.maintenance.impl;

public class EmergencyRepair extends Repair {// TODO: Milestone 3.2
    int gravity;

    public EmergencyRepair(String repairId, String location, int gravity, int nrVehiclesNeeded, int nrWorkersNeeded) {
        super(repairId, RepairType.EMERGENCY_REPAIR, location, -1, nrVehiclesNeeded, nrWorkersNeeded);
        this.gravity = gravity;
    }

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }
}
