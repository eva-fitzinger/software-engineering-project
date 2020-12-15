package at.jku.softengws20.group1.participants.roadNetwork;

public class Position {
    private Road road;
    private double roadPosition;

    public Position(Road road, double roadPosition) {
        this.road = road;
        this.roadPosition = roadPosition;
    }

    public void setRoad(Road road) {
        this.road = road;
    }

    public void setRoadPosition(double roadPosition) {
        this.roadPosition = roadPosition;
    }

    public Road getRoad() {
        return road;
    }

    public double getRoadPosition() {
        return roadPosition;
    }
}
