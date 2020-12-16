package at.jku.softengws20.group1.controlsystem.gui.model;

import at.jku.softengws20.group1.shared.controlsystem.RoadSegmentStatus;
import at.jku.softengws20.group1.shared.detection.TrafficLoad;
import javafx.beans.property.*;

public class ObservableTrafficLoad implements RoadSegmentStatus {

    private RoadSegment roadSegment;
    private StringProperty roadSegmentName;
    private DoubleProperty trafficLoad;
    private BooleanProperty isOpen;

    public ObservableTrafficLoad(RoadSegment roadSegment, RoadSegmentStatus status) {
        this.roadSegment = roadSegment;
        this.trafficLoad = new SimpleDoubleProperty(status.getTrafficLoad());
        this.isOpen = new SimpleBooleanProperty(status.isOpen());
        this.roadSegmentName = new SimpleStringProperty(roadSegment.getDisplayName());
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

    public void update(RoadSegmentStatus status) {
        if (getRoadSegmentId().equals(status.getRoadSegmentId())) {
            trafficLoad.set(status.getTrafficLoad());
            isOpen.set(status.isOpen());
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
}
