package at.jku.softengws20.group1.controlsystem.gui.model;

import at.jku.softengws20.group1.shared.detection.TrafficLightRule;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class ObservableRule implements TrafficLightRule {

    private RoadSegment roadSegment;
    private DoubleProperty priority;

    public ObservableRule(RoadSegment roadSegment, double priority) {
        this.roadSegment = roadSegment;
        this.priority = new SimpleDoubleProperty(priority);
    }

    @Override
    public String getCrossingId() {
        return null;
    }

    @Override
    public String getIncomingRoadSegmentId() {
        return roadSegment.getId();
    }

    @Override
    public double getPriority() {
        return priority.get();
    }

    public RoadSegment getRoadSegment() {
        return roadSegment;
    }

    public DoubleProperty priorityProperty() {
        return priority;
    }
}
