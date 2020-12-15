package at.jku.softengws20.group1.participants.roadNetwork;

public class RoadNetwork {
    public final Crossing[] crossings;
    public final Road[] roads;
    private double MinX = Double.POSITIVE_INFINITY;
    private double MinY = Double.POSITIVE_INFINITY;
    private double MaxX = Double.NEGATIVE_INFINITY;
    private double MaxY = Double.NEGATIVE_INFINITY;

    public RoadNetwork(Crossing[] crossings, Road[] roads) {
        this.crossings = crossings;
        this.roads = roads;
        for (Crossing crossing : crossings) {
            if (crossing.getPosition().getX() < this.MinX) this.MinX = crossing.getPosition().getX();
            if (crossing.getPosition().getY() < this.MinY) this.MinY = crossing.getPosition().getY();
            if (crossing.getPosition().getX() > this.MaxX) this.MaxX = crossing.getPosition().getX();
            if (crossing.getPosition().getY() > this.MaxY) this.MaxY = crossing.getPosition().getY();
        }
    }

    public double getMinX() {
        return MinX;
    }

    public double getMinY() {
        return MinY;
    }

    public double getMaxX() {
        return MaxX;
    }

    public double getMaxY() {
        return MaxY;
    }
}
