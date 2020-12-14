package at.jku.softengws20.group1.shared.impl.service;

import at.jku.softengws20.group1.shared.controlsystem.Timeslot;
import at.jku.softengws20.group1.shared.maintenance.CarPath;
import at.jku.softengws20.group1.shared.maintenance.MaintenanceInterface;

public abstract class MaintenanceRestService extends BaseService implements MaintenanceInterface {
    protected MaintenanceRestService() {
        super(MaintenanceInterface.URL);
    }

    @Override
    public void notifyApprovedMaintenance(Timeslot approvedTimeslot) {
        post(MaintenanceInterface.NOTIFY_APPROVED_MAINTENANCE_URL, approvedTimeslot);
    }

    @Override
    public void notifyMaintenanceCarArrived(CarPath destination) {
        post(MaintenanceInterface.NOTIFY_MAINTENANCE_CAR_ARRIVED_URL, destination);
    }
}
