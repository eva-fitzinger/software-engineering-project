package at.jku.softengws20.group1.controlsystem.gui.model;

import at.jku.softengws20.group1.shared.detection.TrafficLightRule;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ObservableRule implements TrafficLightRule {

    private RoadSegment roadSegment;
    private DoubleProperty priority;
    private StringProperty scenario;

    public ObservableRule(RoadSegment roadSegment, double priority, TrafficScenarioModel scenario) {
        this.roadSegment = roadSegment;
        this.priority = new SimpleDoubleProperty(priority);
        this.scenario = new SimpleStringProperty(scenario.getName());
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

    public StringProperty scenarioProperty() {
        return scenario;
    }
}
