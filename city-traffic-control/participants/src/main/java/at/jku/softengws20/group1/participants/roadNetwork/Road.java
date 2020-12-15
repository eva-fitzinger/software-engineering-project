package at.jku.softengws20.group1.participants.roadNetwork;

import at.jku.softengws20.group1.participants.simulation.Participant;

import javax.servlet.http.Part;
import java.util.Comparator;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Road {
    private final String id; //not unique, as this graph has to be represented as directed
    private final Crossing start;
    private final Crossing end;
    private final double length;
    private boolean isClosed;
    private double speedLimit;
    private double estimatedSpeed;
    private TreeSet<Participant> participants = new TreeSet<Participant>((o1, o2) -> {
        if(o1.getPosition().getRoad() != this) return -1;
        if(o2.getPosition().getRoad() != this) return 1;
        return Double.compare(o1.getPosition().getRoadPosition(), o2.getPosition().getRoadPosition());
    });

    public Road(String id, Crossing start, Crossing end, double length, double defaultSpeedLimit) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.length = length;
        isClosed = false;
        speedLimit = defaultSpeedLimit;
        estimatedSpeed = defaultSpeedLimit;
        start.addRoad(this);
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

    public synchronized void addParticipant(Participant participant) {
        participants.add(participant);
    }

    public synchronized void tick() {
        participants.clear();
    }

    public synchronized Participant getNext(Participant participant) {
        return participants.higher(participant);
    }
}
