package at.jku.softengws20.group1.shared.detection;

public interface DetectionInterface<T extends TrafficLoad, T1 extends TrafficLightRule, T2 extends CarPosition> {
    String URL = "/detection";

    String GET_TRAFFIC_LOAD_URL = "trafficLoad";
    T[] getTrafficLoad();

    String SET_TRAFFIC_LIGHT_RULES_URL = "setTrafficLightRules";
    void setTrafficLightRules(T1[] rules);

    String SET_CAR_POSITION = "setCarPosition";
    void setCarPosition(T2[] position);
}
