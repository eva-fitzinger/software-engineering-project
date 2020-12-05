package at.jku.softengws20.group1.controlsystem.service;

import at.jku.softengws20.group1.controlsystem.model.TrafficLoad;
import at.jku.softengws20.group1.interfaces.impl.DetectionRestService;
import org.springframework.stereotype.Service;

@Service
public class DetectionService extends DetectionRestService<TrafficLoad> {
    public DetectionService() {
        super(TrafficLoad[].class);
    }
}
