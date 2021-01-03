package at.jku.softengws20.group1.participants.restservice;

import at.jku.softengws20.group1.shared.impl.model.CarPosition;
import at.jku.softengws20.group1.shared.impl.model.TrafficLightRule;
import at.jku.softengws20.group1.shared.impl.model.TrafficLoad;
import at.jku.softengws20.group1.shared.impl.service.DetectionRestService;
import org.springframework.stereotype.Service;

@Service
public class ParticipantsDetectionSystemService extends DetectionRestService<TrafficLoad, TrafficLightRule, CarPosition> {
    public ParticipantsDetectionSystemService() {
        super(TrafficLoad[].class);
    }
}
