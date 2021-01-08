package at.jku.softengws20.group1.shared.impl.service;

import at.jku.softengws20.group1.shared.controlsystem.MaintenanceRequest;
import at.jku.softengws20.group1.shared.maintenance.MaintenanceInterface;

public abstract class MaintenanceRestService extends BaseService implements MaintenanceInterface {
    protected MaintenanceRestService() {
        super(MaintenanceInterface.URL);
    }

    @Override
    public void notifyApprovedMaintenance(MaintenanceRequest approvedMaintenanceRequest) {
        post(MaintenanceInterface.NOTIFY_APPROVED_MAINTENANCE_URL, approvedMaintenanceRequest);
    }

    @Override
    public void notifyMaintenanceCarArrived(String id) {
        post(MaintenanceInterface.NOTIFY_MAINTENANCE_CAR_ARRIVED_URL + "/" + id, null);
    } // shouldn't be used
}
