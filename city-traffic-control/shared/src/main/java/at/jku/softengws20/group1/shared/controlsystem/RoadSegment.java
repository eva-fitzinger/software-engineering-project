package at.jku.softengws20.group1.shared.controlsystem;

public interface RoadSegment<T0 extends Position> {
    String getId();
    String getRoadId();
    String getRoadType();
    int getDefaultSpeedLimit();
    double getLength();
    String getCrossingAId();
    String getCrossingBId();
    T0[] getPath();
}
