package at.jku.softengws20.group1.controlsystem.gui.citymap;

import at.jku.softengws20.group1.controlsystem.gui.model.RoadSegment;
import at.jku.softengws20.group1.shared.impl.model.Crossing;
import at.jku.softengws20.group1.shared.impl.model.Position;
import at.jku.softengws20.group1.shared.impl.model.RoadType;
import javafx.scene.paint.Color;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class RoadSegmentDrawable {

    private static final double BASE_LINE_WIDTH = 2.5;

    private at.jku.softengws20.group1.controlsystem.gui.model.RoadSegment roadSegment;
    private Path path;

    RoadSegmentDrawable(RoadSegment segment, Crossing ca, Crossing cb, Transform globalTransform) {
        this.roadSegment = segment;
        path = new Path();
        path.setStrokeWidth(4);
        if (ca != null) {
            Position pos = globalTransform.transform(ca.getPosition());
            path.getElements().add(new MoveTo(pos.getX(), pos.getY()));
        } else {
            Position pos = globalTransform.transform(roadSegment.getPath()[0]);
            path.getElements().add(new MoveTo(pos.getX(), pos.getY()));
        }
        for (Position p : roadSegment.getPath()) {
            Position pos = globalTransform.transform(p);
            path.getElements().add(new LineTo(pos.getX(), pos.getY()));
        }
        if (cb != null) {
            Position pos = globalTransform.transform(cb.getPosition());
            path.getElements().add(new LineTo(pos.getX(), pos.getY()));
        }

        setStyle(RoadStyle.road(roadSegment.getRoadTypeEnum()));
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

    public void selectSecondary() {
        setStyle(RoadStyle.road(roadSegment.getRoadTypeEnum()).selectSecondary());
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

        static RoadStyle road() {
            return new RoadStyle(Color.BLACK, BASE_LINE_WIDTH);
        }

        static RoadStyle highway() {
            return new RoadStyle(Color.BLACK, BASE_LINE_WIDTH*2);
        }

        static RoadStyle primary() {
            return new RoadStyle(Color.BLACK, BASE_LINE_WIDTH*1.5);
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

        RoadStyle selectSecondary() {
            return new RoadStyle(Color.LIGHTBLUE, lineWidth + 1);
        }
    }
}
