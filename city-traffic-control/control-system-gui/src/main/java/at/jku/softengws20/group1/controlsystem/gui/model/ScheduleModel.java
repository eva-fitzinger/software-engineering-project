package at.jku.softengws20.group1.controlsystem.gui.model;

public class ScheduleModel {
    int fromHHMMSS;
    int toHHMMSS;
    int[] dayOfWeek;

    public int getFromHHMMSS() {
        return fromHHMMSS;
    }

    public int getToHHMMSS() {
        return toHHMMSS;
    }

    public int[] getDayOfWeek() {
        return dayOfWeek;
    }
}
