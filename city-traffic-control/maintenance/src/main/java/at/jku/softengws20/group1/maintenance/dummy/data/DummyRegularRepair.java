package at.jku.softengws20.group1.maintenance.dummy.data;

import at.jku.softengws20.group1.maintenance.impl.RegularRepair;
import at.jku.softengws20.group1.maintenance.impl.Repair;
import at.jku.softengws20.group1.maintenance.impl.RepairType;
import at.jku.softengws20.group1.shared.controlsystem.Timeslot;

import java.util.Date;
import java.util.Random;

import static at.jku.softengws20.group1.maintenance.impl.MaintenanceConstants.*;
import static at.jku.softengws20.group1.shared.Config.*;

/**
 * Dummy class used to create RegularRepairs and Timeslots
 */

public class DummyRegularRepair {


    public static RegularRepair getRegularRepair() {
        Random rand = new Random();

        long duration = (Math.abs(rand.nextLong()) % MAX_DURATION);
        duration = duration == 0 ? MAX_DURATION : duration;

        int vehiclesNeeded = rand.nextInt(MAX_MAINTENANCE_VEHICLES / 2) + 1;

        return new RegularRepair(
                "RR: " + Repair.nextRepairId++, //repairId
                RepairType.REGULAR_REPAIR, // RepairType
                "", //location
                rand.nextInt(5), //priority
                duration, //duration in milliseconds
                vehiclesNeeded, // vehicles needed
                rand.nextInt(MAX_EMPLOYEES_PER_CAR) * vehiclesNeeded + 1
        );
    }

    //for simplicity reasons: this maintenance company is a 24/7 service
    public static Timeslot getDummyTimeSlot(RegularRepair regularRepair) {
        Random rand = new Random();

        Date currentDate = new Date();
        long dateTime = currentDate.getTime();

        Date from = new Date(dateTime + Math.abs(rand.nextLong() % TWO_MONTHS));
        Date to = new Date(from.getTime() + regularRepair.getDuration());

        return new at.jku.softengws20.group1.shared.impl.model.Timeslot(
                from,
                to
        );
    }
}
