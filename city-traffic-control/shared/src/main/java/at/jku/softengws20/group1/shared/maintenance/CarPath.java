package at.jku.softengws20.group1.shared.maintenance;

public interface CarPath {
    String getStartRoadSegmentId();
    /**
     * Denotes the relative position of the start on the road, ranging from 0 to 1
     */
    double getStartRoadPosition();
    String getDestinationRoadSegmentId();
    /**
     * Denotes the relative position of the destination on the road, ranging from 0 to 1
     */
    double getDestinationRoadPosition();
    String getCallbackUri();
}
