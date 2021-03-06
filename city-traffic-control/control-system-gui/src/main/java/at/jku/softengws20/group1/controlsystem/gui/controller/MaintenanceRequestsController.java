package at.jku.softengws20.group1.controlsystem.gui.controller;

import at.jku.softengws20.group1.controlsystem.gui.model.LocalDataRepository;
import at.jku.softengws20.group1.controlsystem.gui.model.ObservableMaintenanceRequest;
import at.jku.softengws20.group1.controlsystem.gui.model.ObservableTimeslot;
import at.jku.softengws20.group1.shared.impl.model.MaintenanceRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MaintenanceRequestsController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<ObservableMaintenanceRequest> tblRequests;

    @FXML
    private TableView<ObservableMaintenanceRequest> tblConfirmedRequests;

    @FXML
    private TableView<ObservableTimeslot> tblTimeslots;

    private ControlSystemApi controlSystemApi = new ControlSystemApi();
    private ObservableList<ObservableMaintenanceRequest> confirmedRequests = FXCollections.observableArrayList();

    private ObservableMaintenanceRequest selectedMaintenanceRequest;

    @FXML
    void btnConfirmTimeslotClicked(ActionEvent event) {
        var selTimeslot = tblTimeslots.getSelectionModel().getSelectedItem();
        if (selectedMaintenanceRequest != null && selTimeslot != null) {
            selectedMaintenanceRequest.setApprovedTimeslot(selTimeslot);
                    var req = new MaintenanceRequest<>(selectedMaintenanceRequest.getRequestId(),
                            selectedMaintenanceRequest.getRequestType(), selectedMaintenanceRequest.getRoadSegmentId(),
                            new ObservableTimeslot[]{selTimeslot});
            controlSystemApi.setApprovedMaintenance(req);
            confirmedRequests.add(selectedMaintenanceRequest);
        }
    }

    @FXML
    void initialize() {
        assert tblRequests != null : "fx:id=\"tblRequests\" was not injected: check your FXML file 'maintenanceRequestsView.fxml'.";
        assert tblConfirmedRequests != null : "fx:id=\"tblConfirmedRequests\" was not injected: check your FXML file 'maintenanceRequestsView.fxml'.";
        assert tblTimeslots != null : "fx:id=\"tblTimeslots\" was not injected: check your FXML file 'maintenanceRequestsView.fxml'.";
    }

    private void initTables(MainViewController mainViewController, LocalDataRepository localDataRepository) {

        var colSegment = new TableColumn<ObservableMaintenanceRequest, String>("Road segment");
        colSegment.setCellValueFactory(cellData -> cellData.getValue().roadSegmentNameProperty());
        tblRequests.getColumns().add(colSegment);
        tblRequests.setItems(localDataRepository.getOpenRequests());

        tblRequests.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedMaintenanceRequest = newValue;
                mainViewController.select(newValue.getRoadSegment());
                tblTimeslots.setItems(newValue.timeslotsProperty());
            }
        });

        var colFrom = new TableColumn<ObservableTimeslot, String>("From");
        colFrom.setCellValueFactory(cellData -> cellData.getValue().fromProperty());
        tblTimeslots.getColumns().add(colFrom);

        var colTo = new TableColumn<ObservableTimeslot, String>("To");
        colTo.setCellValueFactory(cellData -> cellData.getValue().toProperty());
        tblTimeslots.getColumns().add(colTo);

        var colRs = new TableColumn<ObservableMaintenanceRequest, String>("Road segment");
        colRs.setCellValueFactory(cellData -> cellData.getValue().roadSegmentNameProperty());
        tblConfirmedRequests.getColumns().add(colRs);

        var colStart = new TableColumn<ObservableMaintenanceRequest, String>("Start time");
        colStart.setCellValueFactory(cellData -> cellData.getValue().startTimeProperty());
        tblConfirmedRequests.getColumns().add(colStart);

        var colEnd = new TableColumn<ObservableMaintenanceRequest, String>("End time");
        colEnd.setCellValueFactory(cellData -> cellData.getValue().endTimeProperty());
        tblConfirmedRequests.getColumns().add(colEnd);

        tblConfirmedRequests.setItems(confirmedRequests);
    }

    static void open(MainViewController mainViewController, LocalDataRepository localDataRepository) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(MaintenanceRequestsController.class.getResource("/maintenanceRequestsView.fxml"));
        SplitPane root = null;
        try {
            root = fxmlLoader.load();
            MaintenanceRequestsController c = fxmlLoader.getController();
            c.initTables(mainViewController, localDataRepository);
            var scene = new Scene(root, 1000, 800);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

