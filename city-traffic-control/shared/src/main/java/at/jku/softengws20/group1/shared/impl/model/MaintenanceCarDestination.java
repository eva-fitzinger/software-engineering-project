package at.jku.softengws20.group1.shared.impl.model;

public class MaintenanceCarDestination implements at.jku.softengws20.group1.shared.maintenance.MaintenanceCarDestination {

    private String destinationRoadSegmentId;
    private double destinationRoadPosition;
    private String maintenanceCarId;


    public MaintenanceCarDestination() {
    }

    public MaintenanceCarDestination(String destinationRoadSegmentId, double destinationRoadPosition, String maintenanceCarId) {
        this.destinationRoadSegmentId = destinationRoadSegmentId;
        this.destinationRoadPosition = destinationRoadPosition;
        this.maintenanceCarId = maintenanceCarId;
    }

    @Override
    public String getDestinationRoadSegmentId() {
        return destinationRoadSegmentId;
    }

    @Override
    public double getDestinationRoadPosition() {
        return destinationRoadPosition;
    }

    @Override
    public String getMaintenanceCarId() {
        return maintenanceCarId;
    }
}
