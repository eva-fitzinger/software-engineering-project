package at.jku.softengws20.group1.maintenance.impl;

import at.jku.softengws20.group1.maintenance.restservice.ControlSystemService_Maintenance;
import at.jku.softengws20.group1.shared.controlsystem.RoadNetwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * Class which provides the roadNetwork to the MaintenanceSystem. Needed in order to find the
 * destinations for Repairs.
 */

@Service
public class CityMapService implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    ControlSystemService_Maintenance controlAPI;

    RoadNetwork roadNetwork;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        roadNetwork = controlAPI.getRoadNetwork();
    }

    public RoadNetwork getRoadNetwork() {
        return roadNetwork;
    }
}
