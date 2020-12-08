package at.jku.softengws20.group1.controlsystem.gui.viewmodel;

import at.jku.softengws20.group1.controlsystem.gui.ControlSystemService;
import at.jku.softengws20.group1.controlsystem.gui.model.Road;
import at.jku.softengws20.group1.shared.controlsystem.Crossing;
import at.jku.softengws20.group1.shared.controlsystem.RoadSegment;
import at.jku.softengws20.group1.shared.impl.model.RoadNetwork;
import javafx.scene.Group;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class CityTrafficMap extends Group {
    private RoadNetwork<at.jku.softengws20.group1.shared.impl.model.Crossing, at.jku.softengws20.group1.shared.impl.model.RoadSegment, Road> roadNetwork;
    private HashMap<Crossing, CrossingDrawable> crossings = new HashMap<>();
    private HashMap<RoadSegment, RoadSegmentDrawable> roadSegments = new HashMap<>();

    private List<SelectionListener<RoadSegment>> roadSegmentSelectedHandler = new LinkedList<>();
    private List<SelectionListener<Crossing>> crossingSelectedHandler = new LinkedList<>();

    private ZoomableScrollPane pane;
    private CrossingDrawable selectedCrossing = null;
    private RoadSegmentDrawable selectedSegment = null;

    private final int WIDTH = 1000;
    private final int HEIGHT = 800;
    private final double LAT_CORR_FACTOR = 0.8;

    public CityTrafficMap() {

        ControlSystemService controlSystemService = new ControlSystemService();
        roadNetwork = controlSystemService.getRoadNetwork();


        createGraph();

        pane = new ZoomableScrollPane(this);
        pane.setPannable(true);
        pane.setOnMouseClicked(mouseEvent -> deselectAll());
        pane.setOnScroll(scrollEvent -> {
            if (scrollEvent.isControlDown()) {
                pane.zoom(scrollEvent.getDeltaY() / 1000.0);
            }
        });
    }

    public void setOnRoadSegmentSelected(SelectionListener<RoadSegment> e) {
        roadSegmentSelectedHandler.add(e);
    }

    public void setOnCrossingSelected(SelectionListener<Crossing> e) {
        crossingSelectedHandler.add(e);
    }

    public ZoomableScrollPane getPane() {
        return pane;
    }

    private void createGraph() {
        roadNetwork.getCrossings().forEach((Crossing c) -> {
            CrossingDrawable d = new CrossingDrawable(c);
            getChildren().add(d.getShape());
            d.getShape().setOnMouseClicked(mouseEvent -> {
                deselectAll();
                d.select();
                selectedCrossing = d;
                crossingSelectedHandler.forEach(h -> h.onSelect(selectedCrossing.getCrossing()));
                mouseEvent.consume();
            });
            crossings.put(c, d);
        });
        roadNetwork.getRoads().forEach(this::addToGraph);
    }

    private void addToGraph(Road road) {
        road.getRoadSegments().forEach(s -> {
            RoadSegmentDrawable d = new RoadSegmentDrawable(s);
            getChildren().add(d.getPath());
            d.getPath().setOnMouseClicked(mouseEvent -> {
                deselectAll();
                d.select();
                selectedSegment = d;
                roadSegmentSelectedHandler.forEach(h -> h.onSelect(selectedSegment.getRoadSegment()));
                mouseEvent.consume();
            });
            roadSegments.put(s, d);
        });
    }

    private void deselectAll() {
        if (selectedCrossing != null) {
            selectedCrossing.deselect();
            selectedCrossing = null;
            crossingSelectedHandler.forEach(h -> h.onSelect(null));
        }
        if (selectedSegment != null) {
            selectedSegment.deselect();
            selectedSegment = null;
            roadSegmentSelectedHandler.forEach(h -> h.onSelect(null));
        }
    }
}
