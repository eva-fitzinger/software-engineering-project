package at.jku.softengws20.group1.detection.Map;

public class SpeedLimit {
    private int standardSpeedLimit;
    private int speedLimit;

    public void setStandardSpeedLimit(int speedLimit) {
        this.standardSpeedLimit = speedLimit;
    }

    public void setSpeedLimit(final int speedLimit) {
        this.speedLimit = speedLimit;
    }

    public int getSpeedLimit() {
        return speedLimit;
    }

    public void resetSpeedLimit() {
        speedLimit = standardSpeedLimit;
    }


}
