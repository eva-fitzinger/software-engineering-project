package at.jku.softengws20.group1.controlsystem.service;
import at.jku.softengws20.group1.shared.impl.model.MaintenanceRequest;
import at.jku.softengws20.group1.shared.impl.model.RoadSegment;
import at.jku.softengws20.group1.shared.controlsystem.RoadSegmentStatus;
import at.jku.softengws20.group1.shared.impl.model.Timeslot;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class MaintenanceRepository {

    private ArrayList<MaintenanceRequest> maintenanceRequestsToApprove = new ArrayList<MaintenanceRequest>();
    private ArrayList<MaintenanceRequest> approvedMaintenanceRequests = new ArrayList<MaintenanceRequest>();

    public MaintenanceRepository() {
        super();
    }

    public void pushMaintenanceRequestToApprove(MaintenanceRequest maintenanceRequest) {
        maintenanceRequestsToApprove.add(maintenanceRequest);
    }

    public MaintenanceRequest[] getMaintenanceRequestsToApprove() {
        return maintenanceRequestsToApprove.toArray(new MaintenanceRequest[0]);
    }

    public void pushApprovedMaintenanceRequests(MaintenanceRequest maintenanceRequest) {
        approvedMaintenanceRequests.add(maintenanceRequest);
        maintenanceRequestsToApprove.removeIf(r -> r.getRequestId().equals(maintenanceRequest.getRequestId()));
    }

    public MaintenanceRequest[] getApprovedMaintenanceRequests() {
        return approvedMaintenanceRequests.toArray(new MaintenanceRequest[0]);
    }

    public MaintenanceRequest[] getCurrentMaintenanceRequests() {

        //cleanup();
        Date now = new Date();

        return approvedMaintenanceRequests.stream()
                .filter(m ->
                        Arrays.stream(m.getTimeSlots())
                                .filter(t -> t.getFrom().compareTo(now) <= 0 && t.getTo().compareTo(now) >= 0 )
                                .count() > 0)
                .toArray(MaintenanceRequest[]::new );
    }

    public void cleanup() {
        for (MaintenanceRequest maintenanceRequest : approvedMaintenanceRequests) {
            Date now = new Date();
            if (Arrays.stream(maintenanceRequest.getTimeSlots()).filter(r -> r.getTo().before(now)).count() == 0) {
                // all timeslots are in the past -> delete it from maintenanceRequestList
                approvedMaintenanceRequests.remove(maintenanceRequest);
            }
        }
    }

}
