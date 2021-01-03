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
        //enterTestData();
    }

    public void pushMaintenanceRequestToApprove(MaintenanceRequest maintenanceRequest) {
        maintenanceRequestsToApprove.add(maintenanceRequest);
    }

    public MaintenanceRequest[] getMaintenanceRequestsToApprove() {
        return maintenanceRequestsToApprove.toArray(new MaintenanceRequest[0]);
    }

    public void pushApprovedMaintenanceRequests(MaintenanceRequest maintenanceRequest) {
        approvedMaintenanceRequests.add(maintenanceRequest);
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

    private void enterTestData()  {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy kk:mm:ss", Locale.GERMAN);
        formatter.setTimeZone(TimeZone.getTimeZone("CET"));

        Timeslot[] timeslots1 = new Timeslot[3];
        try {
            timeslots1[0] = new Timeslot( formatter.parse("03-01-2021 08:00:00"),formatter.parse("03-01-2021 20:00:00"));
            timeslots1[1] = new Timeslot( formatter.parse("04-01-2021 08:00:00"),formatter.parse("04-01-2021 20:00:00"));
            timeslots1[2] = new Timeslot( formatter.parse("05-01-2021 08:00:00"),formatter.parse("05-01-2021 20:00:00"));
            approvedMaintenanceRequests.add( new MaintenanceRequest("type", "223870427_0", timeslots1));

            Timeslot[] timeslots2 = new Timeslot[3];
            timeslots2[0] = new Timeslot( formatter.parse("03-01-2021 08:00:00"),formatter.parse("03-01-2021 20:00:00"));
            timeslots2[1] = new Timeslot( formatter.parse("04-01-2021 08:00:00"),formatter.parse("04-01-2021 20:00:00"));
            timeslots2[2] = new Timeslot( formatter.parse("05-01-2021 08:00:00"),formatter.parse("05-01-2021 20:00:00"));
            approvedMaintenanceRequests.add( new MaintenanceRequest("type", "9679128_5", timeslots2));
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}
