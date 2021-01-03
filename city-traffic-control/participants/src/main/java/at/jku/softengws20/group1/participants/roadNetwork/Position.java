package at.jku.softengws20.group1.participants.roadNetwork;

public class Position {
    private Road road;
    private double roadPosition;

    public Position(Road road, double roadPosition) {
        if(road==null) {
            int deb =0;
        }
        this.road = road;
        this.roadPosition = roadPosition;
    }

    public void setRoad(Road road) {
        if(road==null) {
            int deb =0;
        }
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

    public Coordinate getCoordinate() {
        assert (road != null);
        return new Coordinate(
                road.getStart().getPosition().getX() + (road.getEnd().getPosition().getX() - road.getStart().getPosition().getX()) * roadPosition / road.getLength(),
                road.getStart().getPosition().getY() + (road.getEnd().getPosition().getY() - road.getStart().getPosition().getY()) * roadPosition / road.getLength()
        );
    }
}
