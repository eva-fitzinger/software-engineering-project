package at.jku.softengws20.group1.controlsystem.gui.model;

import java.util.LinkedList;
import java.util.List;

public class Road extends at.jku.softengws20.group1.shared.impl.model.Road {
    private List<RoadSegment> roadSegments = new LinkedList<>();

    public List<RoadSegment> getRoadSegments() {
        return roadSegments;
    }
}
