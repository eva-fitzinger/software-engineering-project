package at.jku.softengws20.group1.interfaces.detection;

public interface DetectionInterface {
    TrafficLoad[] getTrafficLoad();
    void setTrafficLightRules(TrafficLightRule[] rules);
    void registerCarForTrafficLight(CarPosition position);
}
