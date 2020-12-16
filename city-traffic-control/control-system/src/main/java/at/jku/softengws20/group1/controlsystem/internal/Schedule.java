package at.jku.softengws20.group1.controlsystem.internal;

import java.util.Arrays;

public class Schedule {
    private int fromHHMMSS;
    private int toHHMMSS;
    private int[] dayOfWeek;

    public Schedule(int fromHHMMSS, int toHHMMSS, int[] dayOfWeek) {
        this.fromHHMMSS = fromHHMMSS;
        this.toHHMMSS = toHHMMSS;
        this.dayOfWeek = dayOfWeek;
    }

    public int getFromHHMMSS() {
        return fromHHMMSS;
    }

    public int getToHHMMSS() {
        return toHHMMSS;
    }

    public int[] getDayOfWeek() {
        return dayOfWeek;
    }

    public boolean containsTime (int timeHHSSMM, int dayOfWeek) {
        return fromHHMMSS <= timeHHSSMM && timeHHSSMM <= toHHMMSS && Arrays.stream(this.dayOfWeek).filter(d -> d == dayOfWeek).count() !=0 ;
    }

}
