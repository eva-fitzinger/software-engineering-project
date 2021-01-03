package at.jku.softengws20.group1.shared.impl.model;

public class CarPath implements at.jku.softengws20.group1.shared.maintenance.CarPath {

    private String startRoadSegmentId;
    private double startRoadPosition;
    private String destinationRoadSegmentId;
    private double destinationRoadPosition;
    private String callbackUri;

    public CarPath() {
    }

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
