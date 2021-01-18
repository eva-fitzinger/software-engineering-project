package at.jku.softengws20.group1.maintenance.dummy.data;

import at.jku.softengws20.group1.maintenance.impl.RegularRepair;
import at.jku.softengws20.group1.maintenance.impl.Repair;
import at.jku.softengws20.group1.maintenance.impl.RepairType;
import at.jku.softengws20.group1.shared.controlsystem.Timeslot;

import java.util.Date;
import java.util.Random;

import static at.jku.softengws20.group1.shared.Config.*;

public class DummyRegularRepair {
    public static final long MAX_DURATION = 28800000L;
    public static final long ONE_MONTH = 2629800000L;
    public static final long TWO_MONTHS = ONE_MONTH*2;

    public static RegularRepair getRegularRepair() {
        Random rand = new Random();

        return new RegularRepair(
                "RR: " + Repair.nextRepairId++, //repairId
                RepairType.REGULAR_REPAIR, // RepairType
                "", //location
                rand.nextInt(5), //priority
                Math.abs(rand.nextLong()) % MAX_DURATION, //duration in milliseconds
                rand.nextInt(MAX_MAINTENANCE_VEHICLES / 2) + 1, // vehicles needed
                rand.nextInt(MAX_EMPLOYEES)
        );
    }

    //for simplicity reasons: this maintenance company is a 24/7 service
    public static Timeslot getDummyTimeSlot(RegularRepair regularRepair) {
        Random rand = new Random();

        Date currentDate = new Date();
        long dateTime = currentDate.getTime();

        Date from = new Date(dateTime + Math.abs(rand.nextLong() % TWO_MONTHS));
        Date to = new Date(from.getTime() + regularRepair.getDuration());

        Timeslot timeslot = new at.jku.softengws20.group1.shared.impl.model.Timeslot(
                from,
                to
        );
        return timeslot;
    }
}
