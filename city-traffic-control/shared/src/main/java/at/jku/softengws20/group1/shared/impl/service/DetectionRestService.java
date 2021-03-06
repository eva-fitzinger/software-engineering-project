package at.jku.softengws20.group1.shared.impl.service;

import at.jku.softengws20.group1.shared.detection.CarPosition;
import at.jku.softengws20.group1.shared.detection.DetectionInterface;
import at.jku.softengws20.group1.shared.detection.TrafficLightRule;
import at.jku.softengws20.group1.shared.detection.TrafficLoad;

public abstract class DetectionRestService<T0 extends TrafficLoad, T1 extends TrafficLightRule, T2 extends CarPosition>
        extends BaseService implements DetectionInterface<T0, T1, T2> {

    private Class<T0[]> trafficLoadType;

    public DetectionRestService(Class<T0[]> trafficLoadType) {
        super(DetectionInterface.URL);
        this.trafficLoadType = trafficLoadType;
    }

    @Override
    public T0[] getTrafficLoad() {
        return successBodyOrNull(DetectionInterface.GET_TRAFFIC_LOAD_URL, trafficLoadType);
    }

    @Override
    public void setTrafficLightRules(TrafficLightRule[] rules) {
        post(DetectionInterface.SET_TRAFFIC_LIGHT_RULES_URL, rules);
    }

    @Override
    public void setCarPosition(CarPosition[] position) {
        post(DetectionInterface.SET_CAR_POSITION, position);
    }
}
