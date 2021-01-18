package at.jku.softengws20.group1.maintenance.dummy.data;

import at.jku.softengws20.group1.maintenance.impl.*;

import java.util.*;

import static at.jku.softengws20.group1.shared.Config.*;

import at.jku.softengws20.group1.shared.Config;

import static at.jku.softengws20.group1.maintenance.impl.Constants.*;

public class DummyEmergencyRepair {

    public static EmergencyRepair getEmergencyRepair(Date currentDate) {
        List<RepairType> repairTypes = List.of(RepairType.EMERGENCY_REPAIR, RepairType.ACCIDENT, RepairType.THUNDERSTORM);
        Random rand = new Random();

        long duration = (Math.abs(rand.nextLong()) % MAX_DURATION);
        duration = duration == 0 ? MAX_DURATION : duration;

        int vehiclesNeeded = rand.nextInt(MAX_MAINTENANCE_VEHICLES / 2) + 1;

        return new EmergencyRepair(
                "RR: " + Repair.nextRepairId++, //repairId
                repairTypes.get(rand.nextInt(repairTypes.size() - 1)), //repair Type
                "TODO Random location", //location
                rand.nextInt(5), //gravity
                vehiclesNeeded, // vehicles needed
                rand.nextInt(MAX_EMPLOYEES_PER_CAR) * vehiclesNeeded + 1,
                currentDate,
                duration
        );
    }
}
