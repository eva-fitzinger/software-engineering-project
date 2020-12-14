package at.jku.softengws20.group1.participants.roadNetwork;

import at.jku.softengws20.group1.participants.simulation.Participant;

import javax.servlet.http.Part;
import java.util.Queue;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Road {
    private final String id;
    private final Crossing start;
    private final Crossing end;
    private final double length;
    private boolean isClosed;
    private double speedLimit;
    private double estimatedSpeed;
    private ConcurrentLinkedQueue<Participant> participants = new ConcurrentLinkedQueue<>();

    public Road(String id, Crossing start, Crossing end, double length, double defaultSpeedLimit) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.length = length;
        isClosed = false;
        speedLimit = defaultSpeedLimit;
        estimatedSpeed = defaultSpeedLimit;
        start.addRoad(this);
        end.addRoad(this);
    }

    public String getId() {
        return id;
    }

    public Crossing getStart() {
        return start;
    }

    public double getLength() {
        return length;
    }

    public Crossing getEnd() {
        return end;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public double getSpeedLimit() {
        return speedLimit;
    }

    public double getEstimatedSpeed() {
        return estimatedSpeed;
    }
}
