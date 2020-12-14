package at.jku.softengws20.group1.shared.impl.model;

public class CarPath implements at.jku.softengws20.group1.shared.maintenance.CarPath {

    private final String startRoadSegmentId;
    private final double startRoadPosition;
    private final String destinationRoadSegmentId;
    private final double destinationRoadPosition;
    private final String callbackUri;

    public CarPath(String startRoadSegmentId, double startRoadPosition, String destinationRoadSegmentId, double destinationRoadPosition, String callbackUri) {
        this.startRoadSegmentId = startRoadSegmentId;
        this.startRoadPosition = startRoadPosition;
        this.destinationRoadSegmentId = destinationRoadSegmentId;
        this.destinationRoadPosition = destinationRoadPosition;
        this.callbackUri = callbackUri;
    }

    @Override
    public String getStartRoadSegmentId() {
        return startRoadSegmentId;
    }

    @Override
    public double getStartRoadPosition() {
        return startRoadPosition;
    }

    @Override
    public String getDestinationRoadSegmentId() {
        return destinationRoadSegmentId;
    }

    @Override
    public double getDestinationRoadPosition() {
        return destinationRoadPosition;
    }

    @Override
    public String getCallbackUri() {
        return callbackUri;
    }
}
