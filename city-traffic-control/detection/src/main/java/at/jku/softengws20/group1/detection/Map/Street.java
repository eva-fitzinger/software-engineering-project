package at.jku.softengws20.group1.detection.Map;

import at.jku.softengws20.group1.shared.impl.model.TrafficLoad;

public class Street {
    private final String id;
    private final SpeedLimit speedLimit = new SpeedLimit();
    private final InformationSign informationSign = new InformationSign();
    private final String toCrossing;
    private int numberOfCars = 0;

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

    public void incomingVehicle() {
        numberOfCars++;
    }

    public void outgoingVehicle() {
        numberOfCars++;
    }

    public TrafficLoad getTrafficLoad() {
        return new TrafficLoad(id, null, numberOfCars);
    }



    /*For further Implementation if more time:
        - block roads possible
     */

}
