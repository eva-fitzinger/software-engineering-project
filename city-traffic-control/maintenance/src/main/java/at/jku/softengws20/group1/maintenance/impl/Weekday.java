package at.jku.softengws20.group1.maintenance.impl;

public class Weekday {
    enum Day {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY
    }
    private Day day;
    private int workingHours;
    private int availableWorkers;
    private int availableMaintenanceVehicles;

    public Weekday(Day day, int availableWorkers, int availableMaintenanceVehicles) {
        this.day = day;
        workingHours = day != Day.FRIDAY ? 8 : 6;
        this.availableWorkers = availableWorkers;
        this.availableMaintenanceVehicles = availableMaintenanceVehicles;
    }


}
