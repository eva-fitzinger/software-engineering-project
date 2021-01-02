package at.jku.softengws20.group1.controlsystem.gui.model;

import at.jku.softengws20.group1.shared.impl.model.Timeslot;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import java.util.Date;

public class ObservableTimeslot extends Timeslot {

    private StringProperty fromString;
    private StringProperty toString;

    public ObservableTimeslot(Date from, Date to) {
        super(from, to);
        this.fromString = new SimpleStringProperty(from.toString());
        this.toString = new SimpleStringProperty(to.toString());
    }

    public ObservableValue<String> fromProperty() {
        return fromString;
    }

    public ObservableValue<String> toProperty() {
        return toString;
    }
}
