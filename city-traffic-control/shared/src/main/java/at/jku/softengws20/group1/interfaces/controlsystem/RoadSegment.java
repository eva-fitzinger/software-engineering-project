package at.jku.softengws20.group1.interfaces.controlsystem;

public interface RoadSegment {
    String getId();
    String getRoadId();
    String getRoadType();
    int getDefaultSpeedLimit();
    double getLength();
    String getCrossingAId();
    String getCrossingBId();
    Position[] getPath();
}
