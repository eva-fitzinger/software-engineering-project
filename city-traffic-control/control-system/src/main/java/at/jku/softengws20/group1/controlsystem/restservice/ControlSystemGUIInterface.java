package at.jku.softengws20.group1.controlsystem.restservice;

import at.jku.softengws20.group1.controlsystem.internal.TrafficScenario;
import at.jku.softengws20.group1.shared.impl.model.MaintenanceRequest;

public interface ControlSystemGUIInterface<T0 extends MaintenanceRequest> {
    TrafficScenario[] getEnabledTrafficScenarios();

    TrafficScenario[] getTrafficScenarios();

    void setApprovedMaintenance(MaintenanceRequest maintenanceRequest);

    T0[] getMaintenanceRequests();
}
