package at.jku.softengws20.group1.shared.impl.service;

import at.jku.softengws20.group1.shared.detection.CarPosition;
import at.jku.softengws20.group1.shared.detection.DetectionInterface;
import at.jku.softengws20.group1.shared.detection.TrafficLightRule;
import at.jku.softengws20.group1.shared.detection.TrafficLoad;

import java.util.Arrays;
import java.util.Collection;

public abstract class DetectionRestService<T0 extends TrafficLoad> extends BaseService implements DetectionInterface<T0> {

    private Class<T0[]> trafficLoadType;

    public DetectionRestService(Class<T0[]> trafficLoadType) {
        super(DetectionInterface.URL);
        this.trafficLoadType = trafficLoadType;
    }

    @Override
    public Collection<T0> getTrafficLoad() {
        return Arrays.asList(successBodyOrNull(DetectionInterface.GET_TRAFFIC_LOAD_URL, trafficLoadType));
    }

    @Override
    public void setTrafficLightRules(TrafficLightRule[] rules) {
        post(DetectionInterface.SET_TRAFFIC_LIGHT_RULES_URL, rules);
    }

    @Override
    public void registerCarForTrafficLight(CarPosition position) {
        post(DetectionInterface.REGISTER_CAR_FOR_TRAFFIC_LIGHT_URL, position);
    }
}
