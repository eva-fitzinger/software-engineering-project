package at.jku.softengws20.group1.detection.Map;

public class Street {
    private final String id;
    private final SpeedLimit speedLimit = new SpeedLimit();
    private final InformationSign informationSign = new InformationSign();

    public Street(String id) {
        this.id = id;
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



    /*For further Implementation if more time:
        - block roads possible
     */

}
