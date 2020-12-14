package at.jku.softengws20.group1.participants.roadNetwork;

public class RoadNetwork {
    public final Crossing[] crossings;
    public final Road[] roads;

    public RoadNetwork(Crossing[] crossings, Road[] roads) {
        this.crossings = crossings;
        this.roads = roads;
    }
}
