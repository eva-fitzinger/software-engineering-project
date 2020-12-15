package at.jku.softengws20.group1.controlsystem.gui.model;

import at.jku.softengws20.group1.shared.impl.model.Crossing;
import at.jku.softengws20.group1.shared.impl.model.RoadType;

public class RoadSegment extends at.jku.softengws20.group1.shared.impl.model.RoadSegment {
    private Road road;
    private Crossing crossingA;
    private Crossing crossingB;

    public RoadType getRoadTypeEnum() {
        return Enum.valueOf(RoadType.class, getRoadType());
    }

    public Road getRoad() {
        return road;
    }

    public void setRoad(Road road) {
        this.road = road;
    }

    public Crossing getCrossingA() {
        return crossingA;
    }

    public void setCrossingA(Crossing crossingA) {
        this.crossingA = crossingA;
    }

    public Crossing getCrossingB() {
        return crossingB;
    }

    public void setCrossingB(Crossing crossingB) {
        this.crossingB = crossingB;
    }

    public String getDisplayName() {
        return getRoad().getName() + "-" + getId();
    }
}
