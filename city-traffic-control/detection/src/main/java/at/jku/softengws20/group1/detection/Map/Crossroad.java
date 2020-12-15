package at.jku.softengws20.group1.detection.Map;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Crossroad {
    private final String id;
    private final Map<String, Street> streets = new HashMap<>();
    private InformationSign informationSign;
    private TrafficLights trafficLight;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public Crossroad(String id) {
        this.id = id;
    }

    public void start() {
        final Map<String, Street> toCrossings = new HashMap<>();
        for(Map.Entry<String, Street> entry : streets.entrySet()) {
            if(entry.getValue().getToCrossing().equals(id)) {
                toCrossings.put(entry.getKey(), entry.getValue());
            }
        }
        trafficLight = new TrafficLights(toCrossings, id);
        executor.submit(trafficLight);
    }

    public void deleteStreet(String id) {
        streets.remove(id);
    }

    //Getter und Setter and reset
    public TrafficLights getTrafficLight() {
        return trafficLight;
    }

    public void putStreet(Street street) {
        streets.put(street.getId(), street);
    }

    public Street getStreet(String streetID) {
        return streets.get(streetID);
    }

    public void setInformationSign(final String text) {
        informationSign.setText(text);
    }

    public void resetInformationSign() {
        informationSign.resetText();
    }

    /*For further Implementation if more time:
        - block roads possible
     */
}
