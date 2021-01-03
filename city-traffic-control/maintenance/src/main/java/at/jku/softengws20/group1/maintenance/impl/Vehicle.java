package at.jku.softengws20.group1.maintenance.impl;

import at.jku.softengws20.group1.shared.maintenance.MaintenanceCarDestination;

public class Vehicle {
    private String id;
     MaintenanceCarDestination destination;
    private boolean arrived;
    private boolean available;

    Vehicle(String id) {
        this.id = id;
    }


    public String getId() {
        return id;
    }

    public void setDestination(MaintenanceCarDestination destination) {
        this.destination = destination;
    }

    public boolean isArrived() {
        return arrived;
    }

    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
