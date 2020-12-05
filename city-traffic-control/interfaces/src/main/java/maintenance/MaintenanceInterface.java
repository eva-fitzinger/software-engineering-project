package maintenance;

import controlsystem.Timeslot;

public interface MaintenanceInterface {
    void notifyApprovedMaintenance(Timeslot approvedTimeslot);
    void notifyMaintenanceCarArrived(MaintenanceCarDestination destination);
}
