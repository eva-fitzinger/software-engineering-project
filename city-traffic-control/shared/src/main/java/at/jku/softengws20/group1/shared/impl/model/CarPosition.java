package at.jku.softengws20.group1.shared.impl.model;

public class CarPosition implements at.jku.softengws20.group1.shared.detection.CarPosition {

    private String carId;
    private String crossingId;
    private String incomingRoadSegmentId;

    public CarPosition() {}

    public CarPosition(String carId, String crossingId, String incomingRoadSegmentId) {
        this.carId = carId;
        this.crossingId = crossingId;
        this.incomingRoadSegmentId = incomingRoadSegmentId;
    }

    @Override
    public String getCarId() {
        return carId;
    }

    @Override
    public String getCrossingId() {
        return crossingId;
    }

    @Override
    public String getIncomingRoadSegmentId() {
        return incomingRoadSegmentId;
    }
}
