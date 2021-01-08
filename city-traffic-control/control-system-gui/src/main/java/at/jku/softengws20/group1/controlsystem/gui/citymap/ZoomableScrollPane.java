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
        scaleTransform = new Scale(zoom, zoom);
        zoomGroup.getTransforms().add(scaleTransform);
    }

    public void zoom(double value) {
        var oldZoom = zoom;
        zoom += value;
        zoom = Math.min(2.2, Math.max(0.2, zoom));
        // very bad approximation...
        if(Math.abs(zoom - oldZoom) > 0.0001) {
            setHvalue(getHvalue() +Math.sqrt(getHvalue()) *2.0 * (zoom-oldZoom)/(zoom*zoom));
            setVvalue(getVvalue() +Math.sqrt(getVvalue()) *2.0 * (zoom-oldZoom)/(zoom*zoom));
            scaleTransform.setX(zoom);
            scaleTransform.setY(zoom);
        }
    }

    public void zoomIn() {
        zoom(0.05);
    }

    public void zoomOut() {
        zoom(-0.05);
    }
}
