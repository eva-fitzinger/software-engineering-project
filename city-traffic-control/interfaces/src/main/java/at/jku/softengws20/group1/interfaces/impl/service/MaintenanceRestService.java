package at.jku.softengws20.group1.interfaces.impl.service;

import at.jku.softengws20.group1.interfaces.controlsystem.Timeslot;
import at.jku.softengws20.group1.interfaces.maintenance.MaintenanceCarDestination;
import at.jku.softengws20.group1.interfaces.maintenance.MaintenanceInterface;

public abstract class MaintenanceRestService extends BaseService implements MaintenanceInterface {
    protected MaintenanceRestService() {
        super(MaintenanceInterface.URL);
    }

    @Override
    public void notifyApprovedMaintenance(Timeslot approvedTimeslot) {
        post(MaintenanceInterface.NOTIFY_APPROVED_MAINTENANCE_URL, approvedTimeslot);
    }

    @Override
    public void notifyMaintenanceCarArrived(MaintenanceCarDestination destination) {
        post(MaintenanceInterface.NOTIFY_MAINTENANCE_CAR_ARRIVED_URL, destination);
    }
}
