package at.jku.softengws20.group1.detection.Map;

public class Street {
    private final String id;
    private final SpeedLimit speedLimit = new SpeedLimit();
    private InformationSign informationSign = new InformationSign();

    public Street(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setSpeedLimit(int speedLimit) {
        this.speedLimit.setSpeedLimit(speedLimit);
    }

    public int getSpeedLimit() {
        return speedLimit.getSpeedLimit();
    }

    public void resetSpeedLimit() {
        speedLimit.resetSpeedLimit();
    }

    public void setInformationSign(final InformationSign informationSign) {
        this.informationSign = informationSign;
    }

    public void resetInformationSign() {
        informationSign.resetText();
    }

    /*For further Implementation if more time:
        - block roads possible
     */

}
