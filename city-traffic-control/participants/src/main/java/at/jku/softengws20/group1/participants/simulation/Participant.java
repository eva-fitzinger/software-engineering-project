package at.jku.softengws20.group1.participants.simulation;

import at.jku.softengws20.group1.participants.navigation.Navigation;
import at.jku.softengws20.group1.participants.roadNetwork.Position;
import at.jku.softengws20.group1.participants.roadNetwork.Road;

import java.util.concurrent.atomic.AtomicInteger;

public class Participant {
    private static AtomicInteger sequence = new AtomicInteger();
    private final int id;
    private final Position destination;
    private final Navigation navigation;
    private Position position;
    private Road nextRoad;
    private double velocity = 0;
    private double acceleration = 0;

    public Participant(Position position, Position destination, Navigation navigation) {
        id = sequence.getAndIncrement();
        this.position = position;
        this.destination = destination;
        this.navigation = navigation;
    }

    public static AtomicInteger getSequence() {
        return sequence;
    }

    public int getId() {
        return id;
    }

    public Position getDestination() {
        return destination;
    }

    public Position getPosition() {
        return position;
    }

    public double getVelocity() {
        return velocity;
    }

    public void updateAcceleration() {
        Participant next = position.getRoad().getNext(this);
        final double minSafetySec = 2;
        final double bufferSec = 10;
        final double minAcceleration = -9.8;
        final double maxAcceleration = 4;
        final double notAccelerateEndBuffer = 100;

        if (next != null) {
            double safetyDist = minSafetySec * velocity + 4;
            double nextPos = next.position.getRoadPosition() + next.velocity * next.velocity / (-minAcceleration * 2);
            double pos = position.getRoadPosition() + velocity * velocity / (-minAcceleration * 2);
            if (pos + safetyDist > nextPos) {
                acceleration = minAcceleration * (1 - (nextPos - pos) / safetyDist);
            } else
                acceleration = Math.min(maxAcceleration, maxAcceleration * (nextPos - pos - safetyDist) / (velocity * 5));
        } else if (position.getRoadPosition() > position.getRoad().getLength() - notAccelerateEndBuffer)
            acceleration = (0.5 - velocity / position.getRoad().getSpeedLimit()) * maxAcceleration; //accelerate slower when at end of road
        else acceleration = maxAcceleration;
        double velLimit = this.id == 1 ? 5 : position.getRoad().getSpeedLimit();
        double velDiff = velocity - velLimit;
        if (velDiff > 0 && velocity > velLimit * 0.5)
            acceleration = Math.min(acceleration, -velDiff / bufferSec); //geschwindigkeit an das maximum anpassen
        if (acceleration < minAcceleration) acceleration = minAcceleration;
        else if (acceleration > maxAcceleration) acceleration = maxAcceleration;
    }

    public boolean updatePosition(double elapsed) {
        System.out.println("Participant " + id +
                ": pos - " + position.getRoad().getId() + "-" + String.format("%f", position.getRoadPosition()) +
                " a: " + String.format("%.4f", acceleration) +
                " v: " + String.format("%.4f", velocity));
        double newPos = position.getRoadPosition() + velocity * elapsed;
        if (position.getRoad().getId().equals(destination.getRoad().getId())) {
            double targetPos = position.getRoad() == destination.getRoad() ? destination.getRoadPosition() : position.getRoad().getLength() - destination.getRoadPosition();
            if (newPos >= targetPos) {
                System.out.println("Participant " + id + " arrived");
                return true;
            }
        }
        while (newPos > position.getRoad().getLength()) {
            newPos -= position.getRoad().getLength();
            Road next = navigation.getNext(position.getRoad().getEnd(), destination);
            position.setRoad(next);
        }
        position.setRoadPosition(newPos);
        position.getRoad().addParticipant(this);
        velocity += elapsed * acceleration;
        if (velocity < 0) velocity = 0;
        return false;
    }
}
