package at.jku.softengws20.group1.participants.roadNetwork;

public class Position {
    private final Road road;
    private final double roadPosition;

    public Position(Road road, double roadPosition) {
        this.road = road;
        this.roadPosition = roadPosition;
    }

    public Road getRoad() {
        return road;
    }

    public double getRoadPosition() {
        return roadPosition;
    }

    public Coordinate getCoordinate() {
        return new Coordinate(
                road.getStart().getPosition().getX()+(road.getEnd().getPosition().getX()-road.getStart().getPosition().getX())*roadPosition,
                road.getStart().getPosition().getY()+(road.getEnd().getPosition().getY()-road.getStart().getPosition().getY())*roadPosition
        );
    }
}
