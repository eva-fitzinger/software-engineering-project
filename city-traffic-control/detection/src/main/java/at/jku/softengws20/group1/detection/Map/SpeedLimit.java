package at.jku.softengws20.group1.detection.Map;

public class SpeedLimit {
    private int standardSpeedLimit = 50;        //Default value
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
