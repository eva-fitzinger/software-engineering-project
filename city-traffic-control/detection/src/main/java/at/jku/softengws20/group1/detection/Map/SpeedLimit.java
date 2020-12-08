package at.jku.softengws20.group1.detection.Map;

public class SpeedLimit {
    private int standardSpeedLimit;
    private int speedLimit;

    public SpeedLimit(final int standardSpeedLimit) {
        this.standardSpeedLimit = standardSpeedLimit;
        this.speedLimit = standardSpeedLimit;
    }

    public SpeedLimit() {}



    public void setSpeedLimit(final int speedLimit) {
        this.speedLimit = speedLimit;
    }

    public void resetSpeedLimit() {
        speedLimit = standardSpeedLimit;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }
}
