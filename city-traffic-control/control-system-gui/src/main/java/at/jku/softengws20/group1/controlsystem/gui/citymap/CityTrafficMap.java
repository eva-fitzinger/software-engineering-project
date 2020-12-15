package at.jku.softengws20.group1.controlsystem.gui.citymap;

import at.jku.softengws20.group1.controlsystem.gui.model.LocalDataRepository;
import at.jku.softengws20.group1.controlsystem.gui.model.Road;
import at.jku.softengws20.group1.controlsystem.gui.model.RoadSegment;
import at.jku.softengws20.group1.shared.impl.model.Crossing;
import at.jku.softengws20.group1.shared.impl.model.Position;
import javafx.scene.Group;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CityTrafficMap extends Group {
    private LocalDataRepository dataRepository;

    private HashMap<Crossing, CrossingDrawable> crossings = new HashMap<>();
    private HashMap<RoadSegment, RoadSegmentDrawable> roadSegments = new HashMap<>();

    private List<SelectionListener<RoadSegment>> roadSegmentClickedHandler = new LinkedList<>();
    private List<SelectionListener<Crossing>> crossingClickedHandler = new LinkedList<>();

    private ZoomableScrollPane pane;
    private CrossingDrawable selectedCrossing = null;
    private RoadSegmentDrawable selectedSegment = null;

    public CityTrafficMap() {
        pane = new ZoomableScrollPane(this);
        pane.setPannable(true);
        pane.setOnScroll(scrollEvent -> {
            if (scrollEvent.isControlDown()) {
                pane.zoom(scrollEvent.getDeltaY() / 1000.0);
            }
        });
    }

    public void setDataRepository(LocalDataRepository dataRepository) {
        this.dataRepository = dataRepository;
        Transform globalTransform = position -> new Position(position.getX()*500.0, position.getY()*-500.0);
        createGraph(globalTransform);
    }

    public void setOnRoadSegmentClicked(SelectionListener<RoadSegment> e) {
        roadSegmentClickedHandler.add(e);
    }

    public void setOnCrossingClicked(SelectionListener<Crossing> e) {
        crossingClickedHandler.add(e);
    }

    public ZoomableScrollPane getPane() {
        return pane;
    }

    private void createGraph(Transform globalTransform) {
        for (Crossing c : dataRepository.getCrossings()) {
            CrossingDrawable d = new CrossingDrawable(c, globalTransform);
            getChildren().add(d.getShape());
            d.getShape().setOnMouseClicked(mouseEvent -> {
                crossingClickedHandler.forEach(h -> h.onSelect(d.getCrossing()));
                mouseEvent.consume();
            });
            crossings.put(c, d);
        }
        for (Road r : dataRepository.getRoads()) {
            addToGraph(r, globalTransform);
        }
    }

    private void addToGraph(Road road, Transform globalTransform) {
        road.getRoadSegments().forEach(s -> {
            RoadSegmentDrawable d = new RoadSegmentDrawable(s, s.getCrossingA(), s.getCrossingB(), globalTransform);
            getChildren().add(d.getPath());
            d.getPath().setOnMouseClicked(mouseEvent -> {
                roadSegmentClickedHandler.forEach(h -> h.onSelect(d.getRoadSegment()));
                mouseEvent.consume();
            });
            roadSegments.put(s, d);
        });
    }

    public void selectRoadSegment(RoadSegment roadSegment) {
        deselectRoadSegment();
        roadSegment.getRoad().getRoadSegments().forEach(rs -> roadSegments.get(rs).selectSecondary());
        RoadSegmentDrawable drawable = roadSegments.get(roadSegment);
        drawable.select();
        selectedSegment = drawable;
    }

    public void selectCrossing(Crossing crossing) {
        deselectCrossing();
        CrossingDrawable drawable = crossings.get(crossing);
        if(drawable != null) {
            drawable.select();
        }
        selectedCrossing = drawable;
    }

    public void deselectCrossing(){
        if (selectedCrossing != null) {
            selectedCrossing.deselect();
            selectedCrossing = null;
        }
    }

    public void deselectRoadSegment() {
        if (selectedSegment != null) {
            selectedSegment.getRoadSegment().getRoad().getRoadSegments().forEach(rs -> roadSegments.get(rs).deselect());
            selectedSegment = null;
        }
    }
}