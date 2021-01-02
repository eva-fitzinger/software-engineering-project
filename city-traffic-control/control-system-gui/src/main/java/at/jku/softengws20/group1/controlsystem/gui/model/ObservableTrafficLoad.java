package at.jku.softengws20.group1.controlsystem.gui.model;

import at.jku.softengws20.group1.shared.controlsystem.RoadSegmentStatus;
import javafx.beans.property.*;

public class ObservableTrafficLoad implements RoadSegmentStatus {

    private RoadSegment roadSegment;
    private StringProperty roadSegmentName;
    private DoubleProperty trafficLoad;
    private BooleanProperty isOpen;
    private SimpleIntegerProperty roadState;

    ObservableTrafficLoad(RoadSegment roadSegment, RoadSegmentStatus status) {
        this.roadSegment = roadSegment;
        this.trafficLoad = new SimpleDoubleProperty(status.getTrafficLoad());
        this.isOpen = new SimpleBooleanProperty(status.isOpen());
        this.roadSegmentName = new SimpleStringProperty(roadSegment.getDisplayName());
        this.roadState = new SimpleIntegerProperty(RoadState.getState(status.getTrafficLoad(), !status.isOpen()).ordinal());
    }

    @Override
    public String getRoadSegmentId() {
        return roadSegment.getId();
    }

    @Override
    public boolean isOpen() {
        return isOpen.get();
    }

    @Override
    public double getTrafficLoad() {
        return trafficLoad.get();
    }

    void update(RoadSegmentStatus status) {
        if (getRoadSegmentId().equals(status.getRoadSegmentId())) {
            if(Math.abs(trafficLoad.doubleValue() - status.getTrafficLoad()) > 0.0001) {
                trafficLoad.set(status.getTrafficLoad());
            }
            if(isOpen() != status.isOpen()) {
                isOpen.set(status.isOpen());
            }
            var newState = RoadState.getState(getTrafficLoad(), !isOpen()).ordinal();
            if(roadState.get() != newState) {
                roadState.set(newState);
            }
        }
    }

    public RoadSegment getRoadSegment() {
        return roadSegment;
    }

    public String getRoadSegmentName() {
        return roadSegmentName.get();
    }

    public StringProperty roadSegmentNameProperty() {
        return roadSegmentName;
    }

    public DoubleProperty trafficLoadProperty() {
        return trafficLoad;
    }

    public BooleanProperty isOpenProperty() {
        return isOpen;
    }

    public IntegerProperty roadStateProperty() {
        return roadState;
    }

    public RoadState getRoadState() {
        return RoadState.of(roadState.get());
    }
}

