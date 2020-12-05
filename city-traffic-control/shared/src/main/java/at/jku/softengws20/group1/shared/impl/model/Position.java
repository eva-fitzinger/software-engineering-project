package at.jku.softengws20.group1.shared.impl.model;

public class Position implements at.jku.softengws20.group1.shared.controlsystem.Position {
    private double x;
    private double y;

    public Position() {}

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
