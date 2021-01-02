package at.jku.softengws20.group1.controlsystem.gui.model;

import at.jku.softengws20.group1.shared.impl.model.Crossing;
import at.jku.softengws20.group1.shared.impl.model.RoadType;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RoadSegment extends at.jku.softengws20.group1.shared.impl.model.RoadSegment {
    private Road road;
    private Crossing crossingA;
    private Crossing crossingB;
    private StringProperty displayName;

    public RoadSegment() {
        super();
        displayName = new SimpleStringProperty("");
    }

    public RoadType getRoadTypeEnum() {
        return Enum.valueOf(RoadType.class, getRoadType());
    }

    public Road getRoad() {
        return road;
    }

    public void setRoad(Road road) {
        this.road = road;
        displayName.set(getDisplayName());
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

    public StringProperty displayNameProperty() {
        return displayName;
    }
}
