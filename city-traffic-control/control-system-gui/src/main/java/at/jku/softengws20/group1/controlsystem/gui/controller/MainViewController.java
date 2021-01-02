package at.jku.softengws20.group1.controlsystem.gui.controller;

import at.jku.softengws20.group1.controlsystem.gui.citymap.CityTrafficMap;
import at.jku.softengws20.group1.controlsystem.gui.citymap.ZoomableScrollPane;
import at.jku.softengws20.group1.controlsystem.gui.model.LocalDataRepository;
import at.jku.softengws20.group1.controlsystem.gui.model.ObservableRule;
import at.jku.softengws20.group1.controlsystem.gui.model.ObservableTrafficLoad;
import at.jku.softengws20.group1.controlsystem.gui.model.RoadSegment;
import at.jku.softengws20.group1.shared.impl.model.Crossing;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;

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
    private TableView<ObservableRule> tblActiveRules;

    @FXML
    private Button btnCrossingA;

    @FXML
    private Button btnCrossingB;

    @FXML
    private Button btnCloseRoad;

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
    private ObservableList<ObservableRule> activeRules = FXCollections.observableArrayList();
    private ControlSystemApi controlSystemApi = new ControlSystemApi();

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
        pane.setOnKeyTyped(event -> {
            if ("+".equals(event.getCharacter())) {
                pane.zoomIn();
            } else if ("-".equals(event.getCharacter())) {
                pane.zoomOut();
            }
        });

        cityTrafficMap.setOnRoadSegmentClicked(this::select);
        cityTrafficMap.setOnCrossingClicked(this::select);

        localDataRepository = new LocalDataRepository();
        cityTrafficMap.setDataRepository(localDataRepository);
        UpdateService updateService = new UpdateService(localDataRepository);
        updateService.setPeriod(Duration.seconds(3));
        updateService.start();
    }

    private void initTables() {
        var colSegment = new TableColumn<ObservableTrafficLoad, String>("Road segment");
        colSegment.setCellValueFactory(cellData -> cellData.getValue().roadSegmentNameProperty());
        tblTrafficInformation.getColumns().add(colSegment);

        var colTraffic = new TableColumn<ObservableTrafficLoad, Number>("Cars / minute");
        colTraffic.setCellValueFactory(cellData -> cellData.getValue().trafficLoadProperty());
        tblTrafficInformation.getColumns().add(colTraffic);
        tblTrafficInformation.setItems(trafficInformationData);

        tblTrafficInformation.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                select(newValue.getRoadSegment());
            }
        });

        var colRoadSegment = new TableColumn<ObservableRule, String>("Road segment");
        colRoadSegment.setCellValueFactory(cellData -> cellData.getValue().getRoadSegment().displayNameProperty());
        var colPriority = new TableColumn<ObservableRule, Double>("Green light priority");
        colPriority.setCellValueFactory(cellData -> cellData.getValue().priorityProperty().asObject());
        tblActiveRules.getColumns().add(colRoadSegment);
        tblActiveRules.getColumns().add(colPriority);

        tblActiveRules.setItems(activeRules);
        tblActiveRules.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->{
            if(newValue != null) {
                select(newValue.getRoadSegment());
            }
        });
    }

    void select(Crossing crossing) {
        selectedCrossing = crossing;
        cityTrafficMap.selectCrossing(crossing);
        updateCrossingView();
    }

    void select(RoadSegment roadSegment) {
        selectedRoadSegment = roadSegment;
        btnCloseRoad.setText(localDataRepository.getTrafficInformation(roadSegment.getId()).isOpen() ? "Close road" : "Open road");
        cityTrafficMap.selectRoadSegment(roadSegment);
        updateRoadSegmentView();
    }

    private void updateCrossingView() {
        trafficInformationData.clear();
        activeRules.clear();
        if (selectedCrossing != null) {
            // todo show view
            lblCrossingId.setText(selectedCrossing.getId());
            trafficInformationData.addAll(localDataRepository.getTrafficInformation(selectedCrossing));
            activeRules.addAll(localDataRepository.getActiveRules(selectedCrossing));
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
        if (selectedRoadSegment != null) {
            select(selectedRoadSegment.getCrossingA());
        }
    }

    @FXML
    void onSelectCrossingB(ActionEvent event) {
        if (selectedRoadSegment != null) {
            select(selectedRoadSegment.getCrossingB());
        }
    }

    @FXML
    void onBtnCloseRoadClicked(ActionEvent event) {
        if (selectedRoadSegment != null) {
            if (localDataRepository.getTrafficInformation(selectedRoadSegment.getId()).isOpen()) {
                controlSystemApi.setRoadClose(selectedRoadSegment.getId());
            } else {
                controlSystemApi.setRoadAvailable(selectedRoadSegment.getId());
            }
        }
    }

    @FXML
    void onOpenMaintenanceRequestsClick(ActionEvent event) {
        MaintenanceRequestsController.open(this, localDataRepository);
    }

    @FXML
    void onCrossingViewClicked(MouseEvent event) {
        if (selectedCrossing != null) {
            cityTrafficMap.selectCrossing(selectedCrossing);
        }
    }

    @FXML
    void onRoadSegmentViewClicked(MouseEvent event) {
        if (selectedRoadSegment != null) {
            cityTrafficMap.selectRoadSegment(selectedRoadSegment);
        }
    }
}
