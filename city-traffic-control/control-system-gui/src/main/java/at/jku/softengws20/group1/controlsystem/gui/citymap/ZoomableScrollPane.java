package at.jku.softengws20.group1.controlsystem.gui.citymap;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.transform.Scale;

public class ZoomableScrollPane extends ScrollPane {
    private Group zoomGroup;
    private Scale scaleTransform;
    private double zoom = 1.0;

    public ZoomableScrollPane(Node content) {
        Group contentGroup = new Group();
        zoomGroup = new Group();
        contentGroup.getChildren().add(zoomGroup);
        zoomGroup.getChildren().add(content);
        setContent(contentGroup);
        scaleTransform = new Scale(zoom, zoom, 0, 0);
        zoomGroup.getTransforms().add(scaleTransform);
    }

    public void zoom(double value) {
        zoom += value;
        scaleTransform.setX(zoom);
        scaleTransform.setY(zoom);
    }
}
