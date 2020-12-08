package at.jku.softengws20.group1.controlsystem.gui.viewmodel;

import at.jku.softengws20.group1.shared.controlsystem.Crossing;
import at.jku.softengws20.group1.shared.controlsystem.Position;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class CrossingDrawable {

    private Crossing crossing;
    private Circle circle;

    private static final int DEFAULT_RADIUS = 5;
    private static final Color DEFAULT_COLOR = Color.GRAY;

    public CrossingDrawable(Crossing crossing) {
        this.crossing = crossing;
        Position p = crossing.getPosition();
        circle = new Circle(p.getX(), p.getY(), DEFAULT_RADIUS, DEFAULT_COLOR);
    }

    public Shape getShape() {
        return circle;
    }

    public void select() {
        circle.setFill(Color.BLUE);
    }

    public void deselect() {
        circle.setFill(DEFAULT_COLOR);
    }

    public Crossing getCrossing() {
        return crossing;
    }
}
