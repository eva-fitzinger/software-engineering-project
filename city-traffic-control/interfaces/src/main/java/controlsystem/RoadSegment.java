package controlsystem;

public interface RoadSegment {
    String getId();
    String getRoadId();
    String getRoadType();
    int getDefaultSpeedLimit();
    double getLength();
    String getCrossingAId();
    String getCrossingBId();
}
