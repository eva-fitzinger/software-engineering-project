package at.jku.softengws20.group1.shared.impl.service;

import at.jku.softengws20.group1.shared.detection.TrafficLightChange;
import at.jku.softengws20.group1.shared.maintenance.CarPath;
import at.jku.softengws20.group1.shared.participants.ParticipantsInterface;

public abstract class ParticipantsRestService extends BaseService implements ParticipantsInterface {
    protected ParticipantsRestService() {
        super(ParticipantsInterface.URL);
    }

    @Override
    public void sendCar(CarPath request) {
        post(ParticipantsInterface.SEND_CAR, request);
    }

    @Override
    public void notifyTrafficLightChanged(TrafficLightChange change) {
        post(ParticipantsInterface.NOTIFY_TRAFFIC_LIGHT_CHANGED, change);
    }
}
