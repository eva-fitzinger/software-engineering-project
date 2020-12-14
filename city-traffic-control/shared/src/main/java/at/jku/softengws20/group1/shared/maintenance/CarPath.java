package at.jku.softengws20.group1.shared.maintenance;

public interface CarPath {
    String getStartRoadSegmentId();
    double getStartRoadPosition();
    String getDestinationRoadSegmentId();
    double getDestinationRoadPosition();
    String getCallbackUri();
}
