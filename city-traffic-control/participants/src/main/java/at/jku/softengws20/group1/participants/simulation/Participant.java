package at.jku.softengws20.group1.participants.simulation;

import at.jku.softengws20.group1.participants.roadNetwork.Position;

import java.util.concurrent.atomic.AtomicInteger;

public class Participant {
    private static AtomicInteger sequence = new AtomicInteger();
    private final int id;
    private final Position destination;
    private Position position;
    private double velocity = 0;
    private double acceleration = 0;

    public Participant(Position position, Position destination) {
        id = sequence.getAndIncrement();
        this.position = position;
        this.destination = destination;
    }

    public int getId() {
        return id;
    }

    public Position getPosition() {
        return position;
    }
}
