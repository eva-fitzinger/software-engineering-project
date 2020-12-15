package at.jku.softengws20.group1.controlsystem.gui.model;

import at.jku.softengws20.group1.shared.detection.TrafficLoad;
import at.jku.softengws20.group1.shared.impl.model.Crossing;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ObservableTrafficLoad implements TrafficLoad {

    private RoadSegment roadSegment;
    private Crossing crossing;
    private IntegerProperty carsWaitingProperty;
    private StringProperty roadSegmentName;

    public ObservableTrafficLoad(RoadSegment roadSegment, Crossing crossing, int carsWaiting) {
        this.roadSegment = roadSegment;
        this.crossing = crossing;
        this.carsWaitingProperty = new SimpleIntegerProperty(carsWaiting);
        this.roadSegmentName = new SimpleStringProperty(roadSegment.getDisplayName());
    }

    @Override
    public String getRoadSegmentId() {
        return roadSegment.getId();
    }

    @Override
    public String getCrossingId() {
        return crossing.getId();
    }

    @Override
    public int getCarsWaiting() {
        return carsWaitingProperty.get();
    }

    public void setCarsWaiting(int carsWaiting) {
        carsWaitingProperty.set(carsWaiting);
    }

    public RoadSegment getRoadSegment() {
        return roadSegment;
    }

    public Crossing getCrossing() {
        return crossing;
    }

    public IntegerProperty carsWaitingProperty() {
        return carsWaitingProperty;
    }

    public String getRoadSegmentName() {
        return roadSegmentName.get();
    }

    public StringProperty roadSegmentNameProperty() {
        return roadSegmentName;
    }
}
