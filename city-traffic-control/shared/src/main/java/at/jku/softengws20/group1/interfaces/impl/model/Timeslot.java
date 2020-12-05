package at.jku.softengws20.group1.interfaces.impl.model;

import java.util.Date;

public class Timeslot implements at.jku.softengws20.group1.interfaces.controlsystem.Timeslot {

    private Date from;
    private Date to;

    public Timeslot() {}

    public Timeslot(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public Date getFrom() {
        return from;
    }

    @Override
    public Date getTo() {
        return to;
    }
}
