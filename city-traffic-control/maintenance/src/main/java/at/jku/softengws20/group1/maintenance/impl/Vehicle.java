package at.jku.softengws20.group1.maintenance.impl;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    private final String id;
    private String destination;
    private boolean arrived;
    private boolean available = true;
    private boolean carOut = false;

    Vehicle(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
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

    public boolean isCarOut() {
        return carOut;
    }

    public void setCarIsGoingOut(boolean carOut) {
        this.carOut = carOut;
    }
}
