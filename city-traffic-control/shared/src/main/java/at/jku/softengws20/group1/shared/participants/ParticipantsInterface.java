package at.jku.softengws20.group1.shared.participants;

import at.jku.softengws20.group1.shared.impl.model.CarPath;
import at.jku.softengws20.group1.shared.impl.model.TrafficLightChange;

public interface ParticipantsInterface {
    String URL = "/participants";

    String SEND_CAR = "sendCar";
    void sendCar(CarPath request);

    String NOTIFY_TRAFFIC_LIGHT_CHANGED = "trafficLightChanged";
    void notifyTrafficLightChanged(TrafficLightChange change);
}