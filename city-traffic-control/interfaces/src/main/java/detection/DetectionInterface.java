package detection;

public interface DetectionInterface {
    TrafficLoad[] getTrafficLoad();
    void setTrafficLightRules(TrafficLightRule[] rules);
    void registerCarForTrafficLight(CarPosition position);
}
