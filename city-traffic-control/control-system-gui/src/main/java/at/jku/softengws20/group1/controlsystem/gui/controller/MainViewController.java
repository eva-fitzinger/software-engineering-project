package at.jku.softengws20.group1.controlsystem.gui.controller;

import at.jku.softengws20.group1.controlsystem.gui.model.*;
import at.jku.softengws20.group1.controlsystem.gui.citymap.CityTrafficMap;
import at.jku.softengws20.group1.controlsystem.gui.citymap.ZoomableScrollPane;
import at.jku.softengws20.group1.shared.impl.model.Crossing;
import at.jku.softengws20.group1.shared.impl.model.TrafficLightRule;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    @FXML
    private BorderPane mainPane;

    @FXML
    private Label lblCrossingId;

    @FXML
    private TableView<ObservableTrafficLoad> tblTrafficInformation;

    @FXML
    private TableView<TrafficLightRule> tblActiveRules;

    @FXML
    private Button btnCrossingA;

    @FXML
    private Button btnCrossingB;

    @FXML
    private Label lblRoadName;

    @FXML
    private Label lblRoadNumber;

    @FXML
    private Label lblRoadType;

    @FXML
    private Label lblMaxSpeed;

    @FXML
    private Label lblSegmentId;

    @FXML
    private Label lblSegmentLength;

    private LocalDataRepository localDataRepository;
    private CityTrafficMap cityTrafficMap;
    private Crossing selectedCrossing;
    private RoadSegment selectedRoadSegment;
    private ObservableList<ObservableTrafficLoad> trafficInformationData = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTables();

        cityTrafficMap = new CityTrafficMap();
        ZoomableScrollPane pane = cityTrafficMap.getPane();
        mainPane.setCenter(pane);

        pane.setOnMouseClicked(mouseEvent -> {
            cityTrafficMap.deselectCrossing();
            cityTrafficMap.deselectRoadSegment();
        });

        cityTrafficMap.setOnRoadSegmentClicked(this::select);
        cityTrafficMap.setOnCrossingClicked(this::select);

        localDataRepository = new LocalDataRepository();
        cityTrafficMap.setDataRepository(localDataRepository);
    }

    private void initTables() {
        var colSegment = new TableColumn<ObservableTrafficLoad, String>("Road segment");
        colSegment.setCellValueFactory(cellData -> cellData.getValue().roadSegmentNameProperty());
        tblTrafficInformation.getColumns().add(colSegment);

        var colTraffic = new TableColumn<ObservableTrafficLoad, Number>("Cars / minute");
        colTraffic.setCellValueFactory(cellData -> cellData.getValue().carsWaitingProperty());
        tblTrafficInformation.getColumns().add(colTraffic);
        tblTrafficInformation.setItems(trafficInformationData);
    }

    private void select(Crossing crossing) {
        selectedCrossing = crossing;
        cityTrafficMap.selectCrossing(crossing);
        updateCrossingView();
    }

    private void select(RoadSegment roadSegment) {
        selectedRoadSegment = roadSegment;
        cityTrafficMap.selectRoadSegment(roadSegment);
        updateRoadSegmentView();
    }

    private void updateCrossingView() {
        trafficInformationData.clear();
        if (selectedCrossing != null) {
            // todo show view
            lblCrossingId.setText(selectedCrossing.getId());
            trafficInformationData.addAll(localDataRepository.getTrafficInformation(selectedCrossing));
            // todo lblCarsWaiting.setText();
        } else {
            // todo hide view
        }
    }

    private void updateRoadSegmentView() {
        if (selectedRoadSegment != null) {
            // todo show view
            lblSegmentId.setText(selectedRoadSegment.getId());
            lblRoadName.setText(selectedRoadSegment.getRoad().getName());
            lblRoadNumber.setText(selectedRoadSegment.getRoad().getNumber());
            lblRoadType.setText(selectedRoadSegment.getRoadType());
            lblMaxSpeed.setText(selectedRoadSegment.getDefaultSpeedLimit() + " km/h");
            lblSegmentLength.setText(String.format("%.2f km", selectedRoadSegment.getLength()));
            btnCrossingA.setText(selectedRoadSegment.getCrossingAId());
            btnCrossingB.setText(selectedRoadSegment.getCrossingBId());
        } else {
            // todo hide view
        }
    }

    @FXML
    void onSelectCrossingA(ActionEvent event) {
        if(selectedRoadSegment != null) {
            select(selectedRoadSegment.getCrossingA());
        }
    }

    @FXML
    void onSelectCrossingB(ActionEvent event) {
        if(selectedRoadSegment != null) {
            select(selectedRoadSegment.getCrossingB());
        }
    }

    @FXML
    void onCrossingViewClicked(MouseEvent event) {
        if(selectedCrossing != null) {
            cityTrafficMap.selectCrossing(selectedCrossing);
        }
    }

    @FXML
    void onRoadSegmentViewClicked(MouseEvent event) {
        if(selectedRoadSegment != null) {
            cityTrafficMap.selectRoadSegment(selectedRoadSegment);
        }
    }
}
