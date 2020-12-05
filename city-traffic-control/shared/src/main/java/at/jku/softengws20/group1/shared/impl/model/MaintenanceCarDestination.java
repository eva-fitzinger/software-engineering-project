package at.jku.softengws20.group1.shared.impl.model;

public class MaintenanceCarDestination implements at.jku.softengws20.group1.shared.maintenance.MaintenanceCarDestination {

    private String destinationRoadSegmentId;
    private String maintenanceCarId;

    public MaintenanceCarDestination() {}

    public MaintenanceCarDestination(String destinationRoadSegmentId, String maintenanceCarId) {
        this.destinationRoadSegmentId = destinationRoadSegmentId;
        this.maintenanceCarId = maintenanceCarId;
    }

    @Override
    public String getDestinationRoadSegmentId() {
        return destinationRoadSegmentId;
    }

    @Override
    public String getMaintenanceCarId() {
        return maintenanceCarId;
    }
}
