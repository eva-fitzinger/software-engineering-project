package at.jku.softengws20.group1.controlsystem.gui.viewmodel;

import at.jku.softengws20.group1.controlsystem.gui.model.RoadSegment;
import at.jku.softengws20.group1.shared.impl.model.RoadType;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;

public class RoadSegmentDrawable {

    private at.jku.softengws20.group1.controlsystem.gui.model.RoadSegment roadSegment;
    private Path path;

    RoadSegmentDrawable(RoadSegment segment) {
        this.roadSegment = segment;
        path = new Path();
        path.setStrokeWidth(2);
        /*Crossing ca = roadSegment.getCrossingA();
        Crossing cb = roadSegment.getCrossingB();
        if (ca != null) {
            path.getElements().add(new MoveTo(ca.getPosition().getX(), ca.getPosition().getY()));
        } else {
            path.getElements().add(new MoveTo(roadSegment.getPath().get(0).getX(), roadSegment.getPath().get(0).getY()));
        }
        for (Position p : roadSegment.getPath()) {
            path.getElements().add(new LineTo(p.getX(), p.getY()));
        }
        if (cb != null) {
            path.getElements().add(new LineTo(cb.getPosition().getX(), cb.getPosition().getY()));
        }

        setStyle(RoadStyle.road(roadSegment.getRoadType()));*/
    }

    public Path getPath() {
        return path;
    }

    public void setStyle(RoadStyle style) {
        path.setStrokeWidth(style.lineWidth);
        path.setStroke(style.strokeColor);
    }

    public void select() {
        setStyle(RoadStyle.road(roadSegment.getRoadTypeEnum()).select());
    }

    public void deselect() {
        setStyle(RoadStyle.road(roadSegment.getRoadTypeEnum()));
    }

    public RoadSegment getRoadSegment() {
        return roadSegment;
    }

    public static class RoadStyle {
        private Color strokeColor = null;
        private Double lineWidth = null;

        private RoadStyle(Color strokeColor, double lineWidth) {
            this.strokeColor = strokeColor;
            this.lineWidth = lineWidth;
        }

        static RoadStyle road() {
            return new RoadStyle(Color.BLACK, 1);
        }

        static RoadStyle road(RoadType roadType) {
            switch (roadType) {
                case MOTORWAY:
                case TRUNK:
                    return RoadStyle.highway();
                case PRIMARY:
                case SECONDARY:
                    return RoadStyle.primary();
                default:
                    return RoadStyle.road();
            }
        }

        static RoadStyle highway() {
            return new RoadStyle(Color.BLACK, 3);
        }

        static RoadStyle primary() {
            return new RoadStyle(Color.BLACK, 2);
        }

        RoadStyle blocked() {
            return new RoadStyle(Color.DARKRED, lineWidth + 2);
        }

        RoadStyle warnLight() {
            return new RoadStyle(Color.ORANGE, lineWidth + 1);
        }

        RoadStyle warn() {
            return new RoadStyle(Color.RED, lineWidth + 3);
        }

        RoadStyle select() {
            return new RoadStyle(Color.BLUE, lineWidth + 2);
        }
    }
}
