package at.jku.softengws20.group1.participants.roadNetwork;

import at.jku.softengws20.group1.participants.simulation.Participant;

import java.util.ArrayList;
import java.util.HashMap;

public class Road {
    private final String id; //not unique, as this graph has to be represented as directed
    private final Crossing start;
    private final Crossing end;
    private final double length;
    private boolean isClosed;
    private double speedLimit;
    private double estimatedSpeed;
    private ArrayList<Participant> participants = new ArrayList<>();
    private HashMap<Participant, Integer> participantIndizes = new HashMap<>();

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

    public Coordinate getScreenOffset() {
        final double length = 0.25;
        double x = start.getPosition().getX() - end.getPosition().getX();
        double y = start.getPosition().getY() - end.getPosition().getY();
        double l = Math.sqrt(x * x + y * y);
        return new Coordinate(y / l * length, -x / l * length);
    }

    public synchronized void addParticipant(Participant participant) {
        participants.add(participant);
    }

    public synchronized void resetParticipants() {
        participants.clear();
        participantIndizes.clear();
    }

    private int compare(Participant o1, Participant o2) {
        if (o1.getPosition().getRoad() != this) return -1;
        if (o2.getPosition().getRoad() != this) return 1;
        return Double.compare(o1.getPosition().getRoadPosition(), o2.getPosition().getRoadPosition());
    }

    public synchronized void sortParticipants() {
        participants.sort(this::compare);
        for (int i = 0; i < participants.size(); i++) {
            participantIndizes.put(participants.get(i), i);
        }
    }

    public synchronized Participant getNext(Participant participant) {
        Integer index = participantIndizes.get(participant);
        if(index == null) {
            int l = 0;
            int r = participants.size();
            while (l < r - 1) {
                int m = (l + r) / 2;
                if (compare(participant, participants.get(m)) < 0) r = m;
                else l = m;
            }
            index = r;
        } else index++;
        if (index == participants.size()) return null;
        else return participants.get(index);
    }
}
