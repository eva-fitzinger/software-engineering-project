package at.jku.softengws20.group1.controlsystem.gui.citymap;

import at.jku.softengws20.group1.shared.controlsystem.Position;
import at.jku.softengws20.group1.shared.impl.model.Crossing;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

public class CrossingDrawable {

    private Crossing crossing;
    private Circle circle;

    private static final int DEFAULT_RADIUS = 10;
    private static final Color DEFAULT_COLOR = Color.GRAY;

    public CrossingDrawable(Crossing crossing, Transform globalTransform) {
        this.crossing = crossing;
        Position p = globalTransform.transform(crossing.getPosition());
        circle = new Circle(p.getX(), p.getY(), DEFAULT_RADIUS, DEFAULT_COLOR);
        circle.setOpacity(0.5);
        circle.setViewOrder(-2.0);
    }

    public Shape getShape() {
        return circle;
    }

    public void select() {
        circle.setFill(Color.BLUE);
        circle.setViewOrder(-3.0);
    }

    public void deselect() {
        circle.setFill(DEFAULT_COLOR);
        circle.setViewOrder(-2.0);
    }

    public Crossing getCrossing() {
        return crossing;
    }
}
