package at.jku.softengws20.group1.controlsystem.gui.model;

import at.jku.softengws20.group1.shared.controlsystem.MaintenanceRequest;
import at.jku.softengws20.group1.shared.controlsystem.Timeslot;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ObservableMaintenanceRequest implements MaintenanceRequest {

    private String requestId;
    private RoadSegment roadSegment;
    private StringProperty roadSegmentName;
    private ObservableTimeslot selectedTimeslot;
    private ObservableList<ObservableTimeslot> timeslots = FXCollections.observableArrayList();
    private String requestType;

    public ObservableMaintenanceRequest(String requestId, RoadSegment roadSegment, Timeslot[] timeslots, String requestType) {
        this.requestId = requestId;
        this.roadSegment = roadSegment;
        this.roadSegmentName = new SimpleStringProperty(roadSegment.getDisplayName());
        this.requestType = requestType;
        for (var t : timeslots) {
            this.timeslots.add(new ObservableTimeslot(t.getFrom(), t.getTo()));
        }
    }

    @Override
    public String getRequestType() {
        return requestType;
    }

    @Override
    public String getRoadSegmentId() {
        return roadSegment.getId();
    }

    @Override
    public Timeslot[] getTimeSlots() {
        return timeslots.toArray(new Timeslot[0]);
    }

    @Override
    public String getRequestId() {
        return requestId;
    }

    public StringProperty roadSegmentNameProperty() {
        return roadSegmentName;
    }

    public ObservableList<ObservableTimeslot> timeslotsProperty() {
        return timeslots;
    }

    public RoadSegment getRoadSegment() {
        return roadSegment;
    }

    public ObservableValue<String> startTimeProperty() {
        return selectedTimeslot.fromProperty();
    }

    public ObservableValue<String> endTimeProperty() {
        return selectedTimeslot.toProperty();
    }

    public void setApprovedTimeslot(ObservableTimeslot selTimeslot) {
        timeslotsProperty().clear();
        timeslotsProperty().add(selTimeslot);
        this.selectedTimeslot = selTimeslot;
    }
}
