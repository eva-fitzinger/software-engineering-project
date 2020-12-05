package at.jku.softengws20.group1.interfaces.maintenance;

import at.jku.softengws20.group1.interfaces.controlsystem.Timeslot;

public interface MaintenanceInterface {
    String URL = "/maintenance";

    String NOTIFY_APPROVED_MAINTENANCE_URL = "notifyApprovedMaintenance";
    void notifyApprovedMaintenance(Timeslot approvedTimeslot);

    String NOTIFY_MAINTENANCE_CAR_ARRIVED_URL = "notifyMaintenanceCarArrived";
    void notifyMaintenanceCarArrived(MaintenanceCarDestination destination);
}
