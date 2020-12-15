package at.jku.softengws20.group1.detection.Map;

public class Street {
    private final String id;
    private final SpeedLimit speedLimit = new SpeedLimit();
    private final InformationSign informationSign = new InformationSign();
    private final String toCrossing;

    public Street(String id, String toCrossing) {
        this.id = id;
        this.toCrossing = toCrossing;
    }

    public String getId() {
        return id;
    }

    public SpeedLimit getSpeedLimit() {
        return speedLimit;
    }

    public InformationSign getInformationSign() {
        return informationSign;
    }

    public String getToCrossing() {
        return toCrossing;
    }

    /*For further Implementation if more time:
        - block roads possible
     */

}
