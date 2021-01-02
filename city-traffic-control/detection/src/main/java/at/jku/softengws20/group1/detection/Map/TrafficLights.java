package at.jku.softengws20.group1.detection.Map;

import at.jku.softengws20.group1.detection.restservice.ParticipantsService;
import at.jku.softengws20.group1.shared.Config;
import at.jku.softengws20.group1.shared.impl.model.TrafficLightChange;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class TrafficLights implements Runnable {
    public static boolean stop = false;
    private final String crossroadId;
    private final float minutesForFullRun = Config.MINUTES_FOR_FULL_TRAFFIC_LIGHT_RUN / Config.REAL_TIME_FACTOR;
    Map<String, HashSet<String>> streets = new HashMap<>();
    Map<String, Double> priority = new HashMap<>();
    private final ParticipantsService participantsService = new ParticipantsService();
    private boolean standardPriority = true;

    public TrafficLights(final Map<String, Street> streets, final String crossroadId) {     //Constructor
        this.crossroadId = crossroadId;
        for (Map.Entry<String, Street> entry : streets.entrySet()) {
            this.streets.put(entry.getKey(), new HashSet<>());
        }
        standardPriority();
    }

    public synchronized void standardPriority() {       //sets the time for each road-green phase
        double time = minutesForFullRun / streets.size();
        for (Map.Entry<String, HashSet<String>> entry : streets.entrySet()) {
            priority.put(entry.getKey(), time);
        }
        standardPriority = true;
    }

    public synchronized void setPriority(String StreetWithPrioID, double prio) {        //set by control system
        double prioStreet = minutesForFullRun * prio;
        double time = (minutesForFullRun - prioStreet) / (streets.size() - 1);
        for (Map.Entry<String, HashSet<String>> entry : streets.entrySet()) {
            if (entry.getKey().equals(StreetWithPrioID)) {
                priority.put(entry.getKey(), prioStreet);
            } else {
                priority.put(entry.getKey(), time);
            }
        }
        standardPriority = false;
    }

    @Override
    public synchronized void run() {
        long time;
        System.out.printf("start traffic light %s\n", crossroadId);
        while (!stop) {
            for (Map.Entry<String, Double> entry : priority.entrySet()) {
                time = (long) (entry.getValue() * 60);
                participantsService.notifyTrafficLightChanged(new TrafficLightChange(crossroadId, new String[]{entry.getKey()}));       // notify Traffic Detection about traffic light change
                System.out.printf("TrafficLight:%s StreetSeg:%s GREEN (Standard Priority: %b)\n", crossroadId, entry.getKey(), standardPriority);
                try {
                    TimeUnit.SECONDS.sleep(time);           // Time to wait till traffic light change
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
