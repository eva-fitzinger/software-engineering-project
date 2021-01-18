package at.jku.softengws20.group1.shared.detection;

public interface DetectionInterface<T extends TrafficLoad, T1 extends TrafficLightRule, T2 extends CarPosition> {
    String URL = "/detection";

    /**
     * Provides the traffic load given by number of Cars per road
     * @return TrafficLoad[] containing the number of cars for each road
     */
    String GET_TRAFFIC_LOAD_URL = "trafficLoad";
    T[] getTrafficLoad();

    /**
     * called by ControlSystem
     * Receives Traffic light rules and sets the priority for the corresponding traffic light
     * @param rules
     */
    String SET_TRAFFIC_LIGHT_RULES_URL = "setTrafficLightRules";
    void setTrafficLightRules(T1[] rules);

    /**
     * called by Participants
     * sets new car positions
     */
    String SET_CAR_POSITION = "setCarPosition";
    void setCarPosition(T2[] position);
}
