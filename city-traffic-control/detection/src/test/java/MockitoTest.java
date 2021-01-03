import at.jku.softengws20.group1.detection.restservice.ControlSystemService;
import at.jku.softengws20.group1.detection.restservice.DetectionController;
import at.jku.softengws20.group1.shared.impl.model.CarPosition;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.junit.*;

import static org.mockito.Mockito.*;

public class MockitoTest {

    @Mock
    DetectionController detectionController;

    @Mock
    ControlSystemService controlSystemService;

    @Test
    public void testVehicles() {
        String crossingId =  controlSystemService.getRoadNetwork().getCrossings()[1].getId();
        String incomingRoadSegmentId =  controlSystemService.getRoadNetwork().getCrossings()[1].getRoadSegmentIds()[0];
        //detectionController.setCarPosition(new CarPosition("1", crossingId,incomingRoadSegmentId));
    }

}