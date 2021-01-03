package at.jku.softengws20.group1.maintenance.impl;

import at.jku.softengws20.group1.shared.maintenance.CarPath;

public class Vehicle {
    private String id;
    CarPath destination;
    private boolean arrived;
    private boolean available;

    Vehicle(String id) {
        this.id = id;
    }


    public String getId() {
        return id;
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
