package at.jku.softengws20.group1.detection.Map;

import at.jku.softengws20.group1.detection.restservice.ParticipantsService;
import org.springframework.beans.factory.annotation.Autowired;
import at.jku.softengws20.group1.shared.Config;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class TrafficLights implements Runnable {
    public static boolean stop = false;
    private final String crossroadId;
    private final float minutesForFullRun = Config.MINUTES_FOR_FULL_TRAFFIC_LIGHT_RUN * Config.REAL_TIME_FACTOR;
    Map<String, List<String>> streets = new HashMap<>();
    Map<String, Double> priority = new HashMap<>();

    @Autowired
    ParticipantsService participantsService;

    public TrafficLights(final Map<String, Street> streets, final String crossroadId) {
        this.crossroadId = crossroadId;
        for(Map.Entry<String, Street> entry : streets.entrySet()) {
            this.streets.put(entry.getKey(), new LinkedList<>());
        }
        standardPriority();
    }

    public void standardPriority() {
        double time = minutesForFullRun / streets.size();
        for(Map.Entry<String, List<String>> entry : streets.entrySet()) {
            priority.put(entry.getKey(), time);
        }
    }

    public Map<String, List<String>> getNumberOfVehicles() {
        return streets;
    }

    public void incomingVehicle(String StreetID ,String CarID) {
        streets.get(StreetID).add(CarID);
    }

    public void setPriority(String StreetID, double prio) {
        double prioStreet = minutesForFullRun * prio;
        double time = (minutesForFullRun - prioStreet) / (streets.size() -1);
        for(Map.Entry<String, List<String>> entry : streets.entrySet()) {
            if(entry.getKey().equals(StreetID)) {
                priority.put(entry.getKey(), prioStreet);
            } else {
                priority.put(entry.getKey(), time);
            }
        }
    }

    private void outgoingCar(List<String> passedCars) {
        participantsService.notifyCarsPassed(passedCars.toArray(new String[passedCars.size()]));
    }

    @Override
    public void run() {
        long time;
        System.out.printf("Traffic light start: %s\n", crossroadId);
        while (!stop) {
            for(Map.Entry<String, Double> entry : priority.entrySet()) {
                time = (long)(entry.getValue()*60);
                int allowedPasses = (int) (Config.PASSED_CAR_PER_MINUTE * entry.getValue());
                List<String> waitingCars = streets.get(entry.getKey());
                List<String> passedCars = new LinkedList<>();
                for (int i = 0; i < allowedPasses && i < waitingCars.size(); i++) {
                    passedCars.add(waitingCars.remove(1));
                }
                if (passedCars.size() > 0) {
                    outgoingCar(passedCars);
                }
                System.out.printf("TrafficLight:%s StreetSeg:%s GREEN\n", crossroadId, entry.getKey());
                try {
                    TimeUnit.SECONDS.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
