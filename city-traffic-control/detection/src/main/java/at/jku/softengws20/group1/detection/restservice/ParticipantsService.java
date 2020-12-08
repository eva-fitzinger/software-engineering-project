package at.jku.softengws20.group1.detection.restservice;

import at.jku.softengws20.group1.shared.impl.service.ParticipantsRestService;
import org.springframework.stereotype.Service;

@Service
public class ParticipantsService extends ParticipantsRestService {

    @Override
    public void notifyCarsPassed(final String[] carIds) {
        super.notifyCarsPassed(carIds);
    }
}
