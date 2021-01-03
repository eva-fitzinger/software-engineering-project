package at.jku.softengws20.group1.controlsystem.service;

import at.jku.softengws20.group1.shared.impl.model.TrafficLoad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import at.jku.softengws20.group1.shared.impl.model.*;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TrafficCapacityRepository {

    private HashMap<RoadSegment , Integer> trafficCapacity = new HashMap<>();

    public TrafficCapacityRepository() {
        init();
    }

    private void init() {
        MapRepository mapRepository = new MapRepository();
        mapRepository.loadMap();
        for (RoadSegment roadSegment: mapRepository.getRoadNetwork().getRoadSegments()) {
            trafficCapacity.put(roadSegment, getCapacity(roadSegment));
        }
    }

    public HashMap<RoadSegment , Integer> getTrafficCapacity() {
        return trafficCapacity;
    }

    public int getCapacity(RoadSegment roadSegment) {
        // calculate capacity of roadSegement
        // ToDo:  calculation of capacity should be refined
        final double CAR_LENGTH = 4.5;
        final int SPEEDBOUNDRY_LOWERHIGER = 50;
        final double HALTEWEG_FACTOR_LOWERSPEED = 0.3;
        final double HALTEWEG_FACTOR_HIGHERSPEED = 0.5;

        double requiredSpacePerCar =
                CAR_LENGTH +
                roadSegment.getDefaultSpeedLimit() * ((roadSegment.getDefaultSpeedLimit() <= SPEEDBOUNDRY_LOWERHIGER) ? HALTEWEG_FACTOR_LOWERSPEED : HALTEWEG_FACTOR_HIGHERSPEED); // halteweg

        return (int)(roadSegment.getLength()*1000.0/requiredSpacePerCar); // loadlength given in kilometer
    }

    public String[] getCapacityString() {
        String[] result = new String[trafficCapacity.size()];
        int i = 0;
        for(Map.Entry<RoadSegment , Integer> entry : trafficCapacity.entrySet()){
            result[i] = entry.getKey().getId() + " " + entry.getValue();
            i++;
        }
        return result;
    }

}
