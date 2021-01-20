package at.jku.softengws20.group1.shared.maintenance;

import at.jku.softengws20.group1.shared.controlsystem.MaintenanceRequest;

public interface MaintenanceInterface<T extends MaintenanceRequest> {
    String URL = "/maintenance";

    /**
     * Schedules the given <a href = "#{@link}">{@link MaintenanceRequest}</a>
     * at the first given Timeslot if the Maintenance Request was sent for approval
     * in the first place. Random <a href = "#{@link}">{@link MaintenanceRequest}</a>
     * are ignored.
     *
     * @param approvedMaintenanceRequest <a href = "#{@link}">{@link MaintenanceRequest}</a>
     *                                   which was approved (typically by the ControlSystem).
     */
    String NOTIFY_APPROVED_MAINTENANCE_URL = "notifyApprovedMaintenance";
    void notifyApprovedMaintenance(T approvedMaintenanceRequest);

    /**
     * Notifies the MaintenanceSystem that one of its maintenance vehicles arrived at it's given
     * destination. The MaintenanceSystem then either sends the car back to the VehicleCenter or
     * marks the car as returned if its destination was the VehicleCenter itself.
     *
     * @param carId the id of the car that arrived at its destination.
     */
    String NOTIFY_MAINTENANCE_CAR_ARRIVED_URL = "notifyMaintenanceCarArrived";
    void notifyMaintenanceCarArrived(String carId);
}
