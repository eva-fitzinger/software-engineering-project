package at.jku.softengws20.group1.maintenance.dummy.data;

import at.jku.softengws20.group1.maintenance.impl.*;

import java.util.*;

import static at.jku.softengws20.group1.maintenance.dummy.data.DummyRegularRepair.MAX_DURATION;

public class DummyEmergencyRepair {

    public static EmergencyRepair getEmergencyRepair(Date currentDate) {
        List<RepairType> repairTypes = List.of(RepairType.EMERGENCY_REPAIR, RepairType.ACCIDENT, RepairType.THUNDERSTORM);
        Random rand = new Random();
        return new EmergencyRepair(
                "RR: " + Repair.nextRepairId++, //repairId
                repairTypes.get(rand.nextInt(repairTypes.size() - 1)), //repair Type
        "TODO Random location", //location
                rand.nextInt(5), //gravity
                rand.nextInt(VehicleCenter.MAX_VEHICLES), // vehicles needed
                rand.nextInt(VehicleCenter.MAX_EMPLOYEES),
                currentDate,
                rand.nextLong() % MAX_DURATION);
    }
}