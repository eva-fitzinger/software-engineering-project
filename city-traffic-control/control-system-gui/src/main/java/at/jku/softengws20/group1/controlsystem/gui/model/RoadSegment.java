package at.jku.softengws20.group1.controlsystem.gui.model;

import at.jku.softengws20.group1.shared.impl.model.Crossing;
import at.jku.softengws20.group1.shared.impl.model.RoadType;

public class RoadSegment extends at.jku.softengws20.group1.shared.impl.model.RoadSegment {
    private Crossing crossingA;

    public RoadType getRoadTypeEnum() {
        return Enum.valueOf(RoadType.class, getRoadType());
    }
}
