package at.jku.softengws20.group1.detection.Map;

import at.jku.softengws20.group1.shared.impl.model.RoadNetwork;

import java.util.HashMap;
import java.util.Map;

public class CityMap {
    private final Map<String, Street> streets = new HashMap<>();
    private final Map<String, Crossroad> crossroads = new HashMap<>();

    public void createCityMap(RoadNetwork roadNetwork) {

        //create roads
        for (int i = 0; i < roadNetwork.getRoadSegments().length ; i++) {
            String id = roadNetwork.getRoadSegments()[i].getId();
            String toCrossroad = roadNetwork.getRoadSegments()[i].getCrossingBId();     //road goes from A  to B -> B is incoming to a crossroad
            streets.put(id, new Street(id, toCrossroad));
            streets.get(id).getSpeedLimit().setStandardSpeedLimit(roadNetwork.getRoadSegments()[i].getDefaultSpeedLimit()); //set default speed limit
        }

        //create crossroads
        for (int i = 0; i < roadNetwork.getCrossings().length; i++) {
            String id = roadNetwork.getCrossings()[i].getId();
            crossroads.put(id, new Crossroad(id));
            for(var rs : roadNetwork.getRoadSegments()) {
                if(rs.getCrossingBId().equals(id)) {
                    crossroads.get(id).putStreet(getStreet(rs.getId()));
                }
            }
        }

        //start traffic lights (running in thread´s)
        crossroads.forEach((x,y) -> y.start());
    }

    public Street getStreet(String id) {
        return streets.get(id);
    }

    public Map<String, Street> getStreets() {
        return streets;
    }

    public Crossroad getCrossroad(String id) {
        return crossroads.get(id);
    }

    public Map<String, Crossroad> getCrossroads() {     //debugging
        return crossroads;
    }

    //I am perfectly aware that there are unused method´s in the implementation.
    //These could be useful for further implementations and where left there on purpose.
}
