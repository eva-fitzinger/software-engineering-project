package at.jku.softengws20.group1.participants.simulation;

import at.jku.softengws20.group1.participants.navigation.Navigation;
import at.jku.softengws20.group1.participants.roadNetwork.Position;
import at.jku.softengws20.group1.participants.roadNetwork.Road;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Simulation implements Runnable {
    private static final double TIME_FACTOR = 0;
    private static final double MAX_COUNT_PER_TICK = 1000;
    private final Navigation navigation;
    private final HashSet<Participant> participants = new HashSet<>();
    private Random random = new Random();
    private double currentNewCount = 0;
    private int targetCount = 500000;
    private final ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public Simulation(Navigation navigation) {
        this.navigation = navigation;
    }

    public synchronized Participant[] getParticipants() {
        return participants.toArray(Participant[]::new);
    }

    public synchronized void addParticipant(Participant participant) {
        participants.add(participant);
    }

    private void generateParticipant() {
        Road start;
        Road end;
        final int maxCount = 20;
        int count = 0;
        do {
            if(count ++ > maxCount) return;
            start = navigation.getRoadNetwork().roads[random.nextInt(navigation.getRoadNetwork().roads.length)];
            end = navigation.getRoadNetwork().roads[random.nextInt(navigation.getRoadNetwork().roads.length)];
        } while (navigation.getNext(start.getEnd(), new Position(end, 0)) == null);
        addParticipant(new Participant(new Position(start, random.nextDouble() * start.getLength()),
                new Position(end, random.nextDouble() * end.getLength()), navigation));
    }

    private void updateCount() {
        int diff = targetCount - participants.size();
        if (diff > 0) {
            currentNewCount += MAX_COUNT_PER_TICK;
            if (currentNewCount > diff) currentNewCount = diff;
            while (currentNewCount >= 1) {
                generateParticipant();
                currentNewCount -= 1;
            }
        }
    }

    private synchronized void tick() {
        ArrayList<Participant> toRemove = new ArrayList<>();
        long ts = System.nanoTime();
        updateCount();
        participants.parallelStream().forEach(Participant::updateAcceleration);
        Arrays.stream(navigation.getRoadNetwork().roads).parallel().forEach(Road::resetParticipants);

        //double elapsed = (System.nanoTime() - ts) * TIME_FACTOR / 1000000000D;
        double elapsed = 1;

        participants.parallelStream().forEach(participant -> {
            if (participant.updatePosition(elapsed)) {
                synchronized (toRemove) {
                    toRemove.add(participant);
                }
            }
        });
        Arrays.stream(navigation.getRoadNetwork().roads).parallel().forEach(Road::sortParticipants);
        for (Participant participant : toRemove) {
            participants.remove(participant);
        }
        toRemove.clear();
        System.out.println("Elapsed: "+(System.nanoTime()-ts)+"ns ("+participants.size()+"#)");
    }

    @Override
    public void run() {
        while (true) {
            tick();
            try {
                if(TIME_FACTOR != 0) Thread.sleep((int) (1000 / TIME_FACTOR));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
