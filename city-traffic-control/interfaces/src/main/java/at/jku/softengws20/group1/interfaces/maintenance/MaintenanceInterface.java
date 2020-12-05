package at.jku.softengws20.group1.interfaces.maintenance;

import at.jku.softengws20.group1.interfaces.controlsystem.Timeslot;

public interface MaintenanceInterface {
    void notifyApprovedMaintenance(Timeslot approvedTimeslot);
    void notifyMaintenanceCarArrived(MaintenanceCarDestination destination);
}
