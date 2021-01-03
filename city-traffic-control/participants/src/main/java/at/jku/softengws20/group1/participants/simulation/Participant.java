package at.jku.softengws20.group1.participants.simulation;

import at.jku.softengws20.group1.participants.navigation.Navigation;
import at.jku.softengws20.group1.participants.roadNetwork.Position;
import at.jku.softengws20.group1.participants.roadNetwork.Road;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
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
    private Participant nextParticipant;
    private double nextParticipantPos;
    private Road nextParticipantRoad;
    private String callback;

    public Participant(Position position, Position destination, Navigation navigation, String callback) {
        id = sequence.getAndIncrement();
        this.position = position;
        this.destination = destination;
        this.navigation = navigation;
        this.callback = callback;
    }

    public int getId() {
        return id;
    }

    public double getAcceleration() {
        return acceleration;
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
        nextParticipant = position.getRoad().getNext(this);
        if (nextParticipant == null) {
            Road nextRoad = navigation.getNext(position.getRoad().getEnd(), destination.getRoad());
            if (nextRoad != null) nextParticipant = nextRoad.getNext(this);
        }
        final double minSafetySec = 2;
        final double bufferSec = 10;
        final double minAcceleration = -9.8;
        final double maxAcceleration = 4;
        final double notAccelerateEndBuffer = 100;

        double breakPath = velocity * velocity / (-minAcceleration * 2);

        //enforce distance to next participant
        if (nextParticipant != null) {
            nextParticipantPos = nextParticipant.position.getRoadPosition();
            nextParticipantRoad = nextParticipant.position.getRoad();
            double otherRoadPosCompensation = nextParticipantRoad != position.getRoad() ? position.getRoad().getLength() : 0;
            double safetyDist = minSafetySec * velocity + 4;
            double nextPosAfterBreak = otherRoadPosCompensation + nextParticipantPos + nextParticipant.velocity * nextParticipant.velocity / (-minAcceleration * 2);
            double posAfterBreak = position.getRoadPosition() + breakPath;
            if (posAfterBreak + safetyDist > nextPosAfterBreak) {
                acceleration = minAcceleration * (1 - (nextPosAfterBreak - posAfterBreak) / safetyDist);
            } else
                acceleration = Math.min(maxAcceleration, maxAcceleration * (nextPosAfterBreak - posAfterBreak - safetyDist) / (50));
        } else if (position.getRoadPosition() > position.getRoad().getLength() - notAccelerateEndBuffer)
            acceleration = (0.5 - velocity / position.getRoad().getSpeedLimit()) * maxAcceleration; //accelerate slower when at end of road
        else acceleration = maxAcceleration;

        //enforce speed limit
        double velLimit = position.getRoad().getSpeedLimit();
        double velDiff = velocity - velLimit;
        if (velDiff > 0 && velocity > velLimit * 0.5)
            acceleration = Math.min(acceleration, -velDiff / bufferSec); //geschwindigkeit an das maximum anpassen

        //enforce traffic lights
        if (!position.getRoad().getEnd().getGreenRoads().contains(position.getRoad())) {
            double distToEnd = position.getRoad().getLength() - position.getRoadPosition();
            if (distToEnd < 2 * breakPath || distToEnd < 10) {
                acceleration = Math.min(acceleration, -(velocity * velocity) / (2 * distToEnd) - 1);
            }
        }

        //enforce physical limits
        if (acceleration < minAcceleration) acceleration = minAcceleration;
        else if (acceleration > maxAcceleration) acceleration = maxAcceleration;
        if (Double.isNaN(acceleration) || Double.isNaN(velocity)) {
            int x = 0;
        }
    }

    public boolean updatePosition(double elapsed) {
        //System.out.println("Participant " + id +
        //        ": pos - " + position.getRoad().getId() + "-" + String.format("%f", position.getRoadPosition()) +
        //        " a: " + String.format("%.4f", acceleration) +
        //        " v: " + String.format("%.4f", velocity));
        double newPos = position.getRoadPosition() + velocity * elapsed;
        if (this.nextParticipant != null && position.getRoad() == nextParticipantRoad && newPos > nextParticipantPos)
            newPos = nextParticipantPos - 0.01;
        if (position.getRoad().getId().equals(destination.getRoad().getId())) {
            double targetPos = position.getRoad() == destination.getRoad() ? destination.getRoadPosition() : position.getRoad().getLength() - destination.getRoadPosition();
            if (newPos >= targetPos) {
                if (callback != null) {
                    var client = HttpClient.newHttpClient();
                    var request = HttpRequest.newBuilder(URI.create(callback)).build();
                    client.sendAsync(request, HttpResponse.BodyHandlers.ofString());
                }
                //System.out.println("Participant " + id + " arrived");
                return true;
            }
        }
        int cnt = 0;
        while (newPos > position.getRoad().getLength()) {
            if (!position.getRoad().getEnd().getGreenRoads().contains(position.getRoad())) {
                int deb = 0;
            }
            if (cnt++ > 100) {
                System.out.println("navigation loop");
                return true;
            }
            if (position.getRoad() == destination.getRoad()) return true;
            newPos -= position.getRoad().getLength();
            Road nextRoad = navigation.getNext(position.getRoad().getEnd(), destination.getRoad());
            if (nextRoad == null) return true; //destination not reachable
            position.setRoad(nextRoad);
        }
        position.setRoadPosition(newPos);
        position.getRoad().addParticipant(this);
        velocity += elapsed * acceleration;
        if (velocity < 0) velocity = 0;
        return false;
    }
}
