package at.jku.softengws20.group1.participants.simulation;

import at.jku.softengws20.group1.participants.navigation.Navigation;
import at.jku.softengws20.group1.participants.roadNetwork.Road;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Simulation implements Runnable {
    private static final double TIME_FACTOR = 100;
    private final Navigation navigation;
    private final HashSet<Participant> participants = new HashSet<>();

    private final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public Simulation(Navigation navigation) {
        this.navigation = navigation;
    }

    public synchronized void addParticipant(Participant participant) {
        participants.add(participant);
    }

    @Override
    public void run() {
        long ts = System.nanoTime();
        ArrayList<Participant> toRemove = new ArrayList<>();
        while (true) {
            for (Participant participant : participants) {
                participant.updateAcceleration();
            }
            for (Road road : navigation.getRoadNetwork().roads) {
                road.tick();
            }
            double elapsed = (System.nanoTime()-ts)*TIME_FACTOR/1000000000D;
            ts = System.nanoTime();
            for (Participant participant : participants) {
                if(participant.updatePosition(elapsed)) {
                    toRemove.add(participant);
                }
            }
            for (Participant participant : toRemove) {
                participants.remove(participant);
            }
            toRemove.clear();
            try {
                Thread.sleep((int)(1000/TIME_FACTOR));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
