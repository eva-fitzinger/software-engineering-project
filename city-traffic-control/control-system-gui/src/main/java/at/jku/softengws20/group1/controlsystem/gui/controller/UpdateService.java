package at.jku.softengws20.group1.controlsystem.gui.controller;

import at.jku.softengws20.group1.controlsystem.gui.model.LocalDataRepository;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;

public class UpdateService extends ScheduledService<Void> {

    private LocalDataRepository repository;
    private ControlSystemApi controlSystemApi = new ControlSystemApi();

    UpdateService(LocalDataRepository repository) {
        this.repository = repository;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                var status = controlSystemApi.getStatus();
                repository.updateTrafficInformation(status);

                var maintenanceRequests = controlSystemApi.getMaintenanceRequests();
                repository.updateMaintenanceRequests(maintenanceRequests);
                return null;
            }
        };
    }
}
