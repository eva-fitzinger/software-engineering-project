package at.jku.softengws20.group1.maintenance.dummy.data;

import at.jku.softengws20.group1.maintenance.impl.*;

import java.util.Date;
import java.util.Random;

import static at.jku.softengws20.group1.maintenance.dummy.data.DummyRegularRepair.MAX_DURATION;

public class DummyEmergencyRepair { // TODO: Milestone 3.2

    public static EmergencyRepair getEmergencyRepair(Date currentDate) {
        Random rand = new Random();
        return new EmergencyRepair(
                "RR: " + Repair.nextRepairId++, //repairId
                "TODO Random location", //location
                rand.nextInt(5), //gravity
                rand.nextInt(VehicleCenter.MAX_VEHICLES), // vehicles needed
                rand.nextInt(VehicleCenter.MAX_EMPLOYEES),
                currentDate,
                rand.nextLong() % MAX_DURATION);
    }
}
