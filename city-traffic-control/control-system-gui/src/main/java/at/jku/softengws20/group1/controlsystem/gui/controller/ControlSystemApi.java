package at.jku.softengws20.group1.controlsystem.gui.controller;

import at.jku.softengws20.group1.controlsystem.gui.model.TrafficScenarioModel;
import at.jku.softengws20.group1.controlsystem.internal.TrafficScenario;
import at.jku.softengws20.group1.controlsystem.restservice.ControlSystemController;
import at.jku.softengws20.group1.shared.controlsystem.ControlSystemInterface;
import at.jku.softengws20.group1.shared.controlsystem.RoadNetwork;
import at.jku.softengws20.group1.shared.impl.model.MaintenanceRequest;
import at.jku.softengws20.group1.shared.impl.model.RoadSegmentStatus;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ControlSystemApi implements ControlSystemInterface<MaintenanceRequest> {

    private static final String BASE_URL = "http://localhost:8080" + ControlSystemInterface.URL + "/";
    private static final HttpClient HTTP_CLIENT = HttpClient.newBuilder().build();
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public TrafficScenarioModel[] getEnabledTrafficScenarios() {
        return doGet(ControlSystemController.GET_ENABLED_TRAFFIC_SCENARIOS_URL, TrafficScenarioModel[].class);
    }

    public TrafficScenario[] getTrafficScenarios() {
        return doGet(ControlSystemController.GET_TRAFFIC_SCENARIOS_URL, TrafficScenario[].class);
    }

    public void setApprovedMaintenance(MaintenanceRequest maintenanceRequest) {
        doPost(ControlSystemController.SET_APPROVED_MAINTENANCE_URL, maintenanceRequest);
    }

    public MaintenanceRequest[] getMaintenanceRequests() {
        return doGet(ControlSystemController.GET_MAINENANCE_REQUESTS_URL, MaintenanceRequest[].class);
    }


    @Override
    public RoadNetwork getRoadNetwork() {
        return doGet(ControlSystemController.GET_ROAD_NETWORK_URL, at.jku.softengws20.group1.controlsystem.gui.model.RoadNetwork.class);
    }

    @Override
    public RoadSegmentStatus[] getStatus() {
        return doGet(ControlSystemController.GET_STATUS_URL, RoadSegmentStatus[].class);
    }

    @Override
    public void requestRoadClosing(MaintenanceRequest request) {
        doPost(ControlSystemController.REQUEST_ROAD_CLOSING_URL, request);
    }

    @Override
    public void setRoadAvailable(String roadSegmentId) {
        doPost(ControlSystemController.SET_ROAD_AVAILABLE_URL, roadSegmentId);
    }

    private <T> T doGet(String url, Class<T> type) {
        try {
            var req = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL + url))
                    .GET()
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> result = HTTP_CLIENT.send(req, HttpResponse.BodyHandlers.ofString());
            if (result.statusCode() != 200) {
                return null;
            }
            try (JsonParser parser = MAPPER.createParser(result.body())) {
                return parser.readValueAs(type);
            }
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void doPost(String url, Object data) {
        try {
            var req = HttpRequest.newBuilder()
                    .uri(new URI(url))
                    .POST(HttpRequest.BodyPublishers.ofString(MAPPER.writeValueAsString(data)))
                    .header("Content-Type", "application/json")
                    .build();
            HTTP_CLIENT.send(req, HttpResponse.BodyHandlers.ofString());
        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
