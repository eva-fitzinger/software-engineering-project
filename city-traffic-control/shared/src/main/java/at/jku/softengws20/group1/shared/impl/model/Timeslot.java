package at.jku.softengws20.group1.shared.impl.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Timeslot implements at.jku.softengws20.group1.shared.controlsystem.Timeslot {

    private Date from;
    private Date to;

    public Timeslot() {}

    public Timeslot(Date from, Date to) {
        this.from = from;
        this.to = to;
    }

    @Override
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    public Date getFrom() {
        return from;
    }

    @Override
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    public Date getTo() {
        return to;
    }
}
