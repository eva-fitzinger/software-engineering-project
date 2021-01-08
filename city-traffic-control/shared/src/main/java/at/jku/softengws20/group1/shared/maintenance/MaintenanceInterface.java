package at.jku.softengws20.group1.shared.maintenance;

import at.jku.softengws20.group1.shared.controlsystem.MaintenanceRequest;

public interface MaintenanceInterface {
    String URL = "/maintenance";

    String NOTIFY_APPROVED_MAINTENANCE_URL = "notifyApprovedMaintenance";
    void notifyApprovedMaintenance(MaintenanceRequest approvedMaintenanceRequest);

    String NOTIFY_MAINTENANCE_CAR_ARRIVED_URL = "notifyMaintenanceCarArrived";
    void notifyMaintenanceCarArrived(String carId);
}
