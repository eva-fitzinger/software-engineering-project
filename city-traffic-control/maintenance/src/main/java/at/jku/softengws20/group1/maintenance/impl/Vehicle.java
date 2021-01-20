package at.jku.softengws20.group1.maintenance.impl;

/**
 * The <a href="#{@link}">{@link Vehicle}</a> class exists for completion. The number of Vehicles is considered
 * in order to only send available vehicles. Vehicles are handled in the vehicle center.
 */

public class Vehicle {
    private final String id;
    private String destination;
    private boolean available = true;
    private boolean carIsGoingOut = false;

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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public boolean isCarIsGoingOut() {
        return carIsGoingOut;
    }

    public void setCarIsGoingOut(boolean carOut) {
        this.carIsGoingOut = carOut;
    }
}
