package at.jku.softengws20.group1.controlsystem.gui.model;

public enum RoadState {
    NORMAL,
    WARN_LIGHT,
    WARN,
    BLOCKED;

    public static RoadState getState(double traffic, boolean blocked) {
        if (blocked)
            return RoadState.BLOCKED;
        if(traffic >= 0.9)
            return RoadState.WARN;
        if (traffic >= 0.7)
            return RoadState.WARN_LIGHT;
        return RoadState.NORMAL;
    }

    public static RoadState of(int ordinal) {
        return RoadState.values()[ordinal];
    }
}