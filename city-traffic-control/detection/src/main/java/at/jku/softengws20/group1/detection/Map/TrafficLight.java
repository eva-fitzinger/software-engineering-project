package at.jku.softengws20.group1.detection.Map;

import at.jku.softengws20.group1.detection.restservice.ParticipantsService;
import at.jku.softengws20.group1.shared.Config;
import at.jku.softengws20.group1.shared.impl.model.TrafficLightChange;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TrafficLight implements Runnable {
    public static boolean stop = false;
    private final String crossroadId;
    private final float minutesForFullRun = Config.MINUTES_FOR_FULL_TRAFFIC_LIGHT_RUN / Config.REAL_TIME_FACTOR;
    private final ParticipantsService participantsService = new ParticipantsService();
    final Map<String, Double> priority = new HashMap<>();
    private boolean standardPriority = true;

    public TrafficLight(final Map<String, Street> streets, final String crossroadId) {     //Constructor
        this.crossroadId = crossroadId;
        double time = minutesForFullRun / streets.size();
        for (Map.Entry<String, Street> entry : streets.entrySet()) {
            priority.put(entry.getKey(), time);
        }
        standardPriority();
    }

    public void standardPriority() {       //sets the time for each road-green phase
        double time = minutesForFullRun / priority.size();
        for (Map.Entry<String, Double> entry : priority.entrySet()) {
            synchronized (priority) {
                entry.setValue(time);
            }
        }
        standardPriority = true;
    }

    public void setPriority(String streetWithPrioID, double prio) {        //set by control system
        double prioStreet = minutesForFullRun * prio;
        priority.put(streetWithPrioID, prioStreet);     //Note, the other streets in the traffic light will not be changed because of the given Street priority. (the time expends)
        standardPriority = false;
    }

    public Map<String, Double> getRules(){        //only for debugging

        //calculate back to given priority
        Map<String, Double> prioPercentage = new HashMap<>();
        for (Map.Entry<String, Double> entry : priority.entrySet()) {
            prioPercentage.put(entry.getKey(), entry.getValue() / minutesForFullRun);
        }
        return prioPercentage;
    }

    @Override
    public void run() {
        long time;
        System.out.printf("start traffic light %s\n", crossroadId);
        while (!stop) {
            for (Map.Entry<String, Double> entry : priority.entrySet()) {
                synchronized (priority) {
                    time = (long) (entry.getValue() * 60);
                }
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
