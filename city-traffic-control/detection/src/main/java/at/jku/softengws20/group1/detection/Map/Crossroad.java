package at.jku.softengws20.group1.detection.Map;

import at.jku.softengws20.group1.shared.impl.model.TrafficLoad;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
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

    public List<TrafficLoad> getNumberOfVehicles() {
        List<TrafficLoad> trafficLoad = new LinkedList<>();
        for (int i = 0; i < trafficLight.getNumberOfVehicles().size(); i++) {
            for(Map.Entry<String, List<String>> entry : trafficLight.getNumberOfVehicles().entrySet()) {
                trafficLoad.add(new TrafficLoad(entry.getKey(), id, entry.getValue().size()));
            }
        }
        return trafficLoad;
    }

    public void start() {
        trafficLight = new TrafficLights(streets, id);
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
