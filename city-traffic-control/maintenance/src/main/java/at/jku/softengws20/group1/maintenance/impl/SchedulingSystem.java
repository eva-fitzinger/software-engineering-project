package at.jku.softengws20.group1.maintenance.impl;

import at.jku.softengws20.group1.maintenance.dummy.data.DummyRegularRepair;
import at.jku.softengws20.group1.maintenance.restservice.ControlSystemService_Maintenance;
import at.jku.softengws20.group1.shared.impl.model.Timeslot;
import at.jku.softengws20.group1.shared.impl.model.MaintenanceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SchedulingSystem {

    @Autowired
    private CityMapService cityMapService;

    private ControlSystemService_Maintenance controlSystemServiceMaintenance = new ControlSystemService_Maintenance();


    private List<Repair> schedule;
    private List<RegularRepair> notApprovedRegularRepairs;

    public SchedulingSystem() {
        schedule = new ArrayList<>();
        notApprovedRegularRepairs = new ArrayList<>();
    }

    public List<Repair> getSchedule() {
        return schedule;
    }


    public void printSchedule() {
        System.out.println("-------- Time Table for this Week ---------");
        schedule.sort((r1, r2) -> {
            if (r1 == r2) {
                return 0;
            }
            return r1.getFrom().before(r2.getFrom()) ? -1 : 1;
        });
        for (Repair repair : schedule) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            System.out.println(formatter.format(repair.getFrom()) + "-"
                    + formatter.format(repair.getTo()) + ":" +
                    repair.getRepairId());
        }
    }

    public void addRegularRepair() {
        RegularRepair regularRepair = DummyRegularRepair.getRegularRepair();
        at.jku.softengws20.group1.shared.impl.model.Timeslot[] timeslots = findAvailableTimeslots(regularRepair);
        regularRepair.setTimeslot(timeslots);
        addRegularRepair(regularRepair);
    }

    private at.jku.softengws20.group1.shared.impl.model.Timeslot[] findAvailableTimeslots(RegularRepair regularRepair) {
        at.jku.softengws20.group1.shared.impl.model.Timeslot timeslot;
        at.jku.softengws20.group1.shared.impl.model.Timeslot[] availableTimeslots = new at.jku.softengws20.group1.shared.impl.model.Timeslot[3];

        for (int i = 0; i < 3; ) {
            timeslot = (Timeslot) DummyRegularRepair.getDummyTimeSlot(regularRepair);

            if (schedule.size() == 0) {
                availableTimeslots[i] = timeslot;
                i++;
            } else {
                for (int j = 0; i < 3 && j < schedule.size() && timeslot.getTo().before(schedule.get(j).getTo()); j++) { //as long as the timeSlot end is before the end of the current schedule
                    //if at beginning
                    if (timeslot.getTo().before(schedule.get(0).getFrom())) {
                        availableTimeslots[i] = timeslot;
                        i++;
                        break;
                    }
                    //if at end
                    else if (timeslot.getFrom().after(schedule.get(schedule.size() - 1).getTo())) {
                        availableTimeslots[i] = timeslot;
                        i++;
                        break;
                    }

                    //if between to scheduled dates
                    else if (schedule.get(j).getTo().before(timeslot.getFrom()) && timeslot.getTo().before(schedule.get(j + 1).getFrom())) {
                        availableTimeslots[i] = timeslot;
                        i++;
                        break;
                    }
                }
            }
        }
        return availableTimeslots;
    }

    public void addRegularRepair(RegularRepair regularRepair) {
        Random rand = new Random();
        //TODO Jakob what is this warning?
        String location = cityMapService.getRoadNetwork()
                .getRoadSegments()[rand.nextInt(cityMapService.getRoadNetwork().getRoadSegments().length)].getId();
        regularRepair.setLocation(location);
        System.out.println("Maintenance:: Regular Repair request sent: " + regularRepair.getRepairId());
        notApprovedRegularRepairs.add(regularRepair);
        MaintenanceRequest<at.jku.softengws20.group1.shared.impl.model.Timeslot> maintenanceRequest =
                new MaintenanceRequest(
                        regularRepair.getRepairId(),
                        "close road",
                        regularRepair.getLocation(),
                        regularRepair.getTimeslot()
                );
        // request permission
        controlSystemServiceMaintenance.requestRoadClosing(maintenanceRequest);
    }

    public void triggerRegularRepairAccepted(at.jku.softengws20.group1.shared.controlsystem.MaintenanceRequest approvedMaintenanceRequest) {
        System.out.println("Maintenance:: Regular Repair accepted: " + approvedMaintenanceRequest.getRequestId());
        Timeslot[] approvedTimeslots = (Timeslot[]) approvedMaintenanceRequest.getTimeSlots();
        RegularRepair regularRepair = notApprovedRegularRepairs.stream().filter(r -> r.getRepairId().equals(approvedMaintenanceRequest.getRequestId())).findAny().orElse(null);

        if (approvedTimeslots == null) {
            System.out.println("Maintenance:: no Timeslots accepted");
            return;
        }
        if (regularRepair == null) {
            System.out.println("Maintenance:: no such Repair");
            return;
        }

        //search for first possible timeslot
        Timeslot timeslot = Arrays.stream(approvedTimeslots).min((r1, r2) -> {
            if (r1 == r2) {
                return 0;
            }
            return r1.getFrom().before(r2.getFrom()) ? -1 : 1;
        }).orElse(null);
        if(timeslot == null){
            return;
        }
        regularRepair.setTime(timeslot.getFrom(), timeslot.getTo());

        //safe first possible Timeslot in schedule
        schedule.add(regularRepair);

        printSchedule();
    }

    public void addEmergencyRepair(EmergencyRepair emergencyRepair) {
        //add right away
        List<Repair> localSchedule = getSchedule();
        if (localSchedule.size() == 0) {
            localSchedule.add(emergencyRepair);
        } else if (emergencyRepair.getTo().before(localSchedule.get(0).getFrom())) {// time is free anyways
            getSchedule().add(emergencyRepair);
        } else { // reschedule
            long difference = emergencyRepair.getTo().getTime() - getSchedule().get(0).getFrom().getTime();
            for (Repair repair : getSchedule()) {
                repair.setTime(new Date(repair.getFrom().getTime() + difference),
                        new Date(repair.getFrom().getTime() + difference));
            }
            //no add to schedule, because car will be sent right away by MaintenanceController
        }
    }
}
