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

    public static RegularRepair getRegularRepair() {
        Random rand = new Random();

        return new RegularRepair(
                "RR: " + Repair.nextRepairId++, //repairId
                RepairType.REGULAR_REPAIR, // RepairType
                "", //location
                rand.nextInt(5), //priority
                rand.nextLong() % MAX_DURATION, //duration in milliseconds
                rand.nextInt(MAX_MAINTENANCE_VEHICLES / 2) + 1, // vehicles needed
                rand.nextInt(MAX_EMPLOYEES)
        );
    }

    //for simplicity reasons: this maintenance company is a 24/7 service
    // todo decide whether you use this
    public static Timeslot[] getDummyTimeSlots(RegularRepair regularRepair) {
        Random rand = new Random();

        Date currentDate = new Date();
        long dateTime = currentDate.getTime();

        Timeslot[] timeslots = new at.jku.softengws20.group1.shared.impl.model.Timeslot[rand.nextInt(4) + 1];
        for (int i = 0; i < timeslots.length; i++) {

            Date from = new Date(dateTime + rand.nextLong());
            Date to = new Date(from.getTime() + regularRepair.getDuration());

            timeslots[i] = new at.jku.softengws20.group1.shared.impl.model.Timeslot(
                    from,
                    to
            );
        }

        return timeslots;
    }


    public static Timeslot getDummyTimeSlot(RegularRepair regularRepair) {
        Random rand = new Random();

        Date currentDate = new Date();
        long dateTime = currentDate.getTime();

        Date from = new Date(dateTime + rand.nextLong());
        Date to = new Date(from.getTime() + regularRepair.getDuration());

        Timeslot timeslot = new at.jku.softengws20.group1.shared.impl.model.Timeslot(
                from,
                to
        );
        return timeslot;
    }
}
