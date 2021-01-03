package at.jku.softengws20.group1.maintenance.impl;

import at.jku.softengws20.group1.maintenance.dummy.data.DummyRegularRepair;
import at.jku.softengws20.group1.maintenance.restservice.ControlSystemService_Maintenance;
import at.jku.softengws20.group1.shared.controlsystem.Timeslot;
import at.jku.softengws20.group1.shared.impl.model.*;

import java.text.SimpleDateFormat;
import java.util.*;


public class SchedulingSystem {

    private static RegularRepair currentRepairApproval;

    private ControlSystemService_Maintenance controlSystemServiceMaintenance = new ControlSystemService_Maintenance();


    public List<Repair> getSchedule() {
        return schedule;
    }

    private List<Repair> schedule;

    public SchedulingSystem() {
        schedule = new ArrayList<>();
    }


    public void printSchedule() {
        System.out.println("-------- Time Table for this Week ---------");
        for (Repair repair : schedule) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            System.out.println(formatter.format(repair.getFrom()) + "-"
                    + formatter.format(repair.getTo()) + ":" +
                    repair.getRepairId());
        }
    }

    public void addRegularRepair() {
        RegularRepair regularRepair = DummyRegularRepair.getRegularRepair();
        at.jku.softengws20.group1.shared.impl.model.Timeslot[] timeslots = findApprovedTimeslots(regularRepair);
        regularRepair.setTimeslot(timeslots);
        addRegularRepair(regularRepair);
    }

    private at.jku.softengws20.group1.shared.impl.model.Timeslot[] findApprovedTimeslots(RegularRepair regularRepair) {
        at.jku.softengws20.group1.shared.impl.model.Timeslot timeslot;
        at.jku.softengws20.group1.shared.impl.model.Timeslot[] approvedTimeslots = new at.jku.softengws20.group1.shared.impl.model.Timeslot[3];

        for (int i = 0; i < 3; ) {
            timeslot = DummyRegularRepair.getDummyTimeSlot(regularRepair);

            if (schedule.size() == 0) {
                approvedTimeslots[i] = timeslot;
                i++;
            } else {
                for (int j = 0; j <= schedule.size() && timeslot.getTo().before(schedule.get(j).getTo()); j++) { //as long as the timeSlot end is before the end of the current schedule
                    //if at beginning
                    if (timeslot.getTo().before(schedule.get(0).getFrom())) {
                        approvedTimeslots[i] = timeslot;
                        i++;
                    }
                    //if at end
                    if (schedule.get(j + 1) == null) {
                        approvedTimeslots[i] = timeslot;
                        i++;
                    }

                    //if between to scheduled dates
                    if (schedule.get(j).getTo().before(timeslot.getFrom()) && timeslot.getTo().before(schedule.get(j + 1).getFrom())) {
                        approvedTimeslots[i] = timeslot;
                        i++;
                    }
                }
            }
        }
        return approvedTimeslots;
    }

    public void addRegularRepair(RegularRepair regularRepair) {//TODO comment on/out
        currentRepairApproval = regularRepair;
        //TODO Jakob what is this warning?
        MaintenanceRequest<at.jku.softengws20.group1.shared.impl.model.Timeslot> maintenanceRequest = new MaintenanceRequest("close road",
                currentRepairApproval.getRepairId(), currentRepairApproval.getTimeslot());
        // request permission
        controlSystemServiceMaintenance.requestRoadClosing(maintenanceRequest);
    }

    public void triggerRegularRepairAccepted(Timeslot approvedTimeslot) {
        currentRepairApproval.setApproved(true);
        //safe in schedule
        setTimeslot(approvedTimeslot);
    }

    private void setTimeslot(Timeslot approvedTimeslot) {
        currentRepairApproval.setTime(approvedTimeslot.getFrom(), approvedTimeslot.getTo());
    }

    public static RegularRepair getCurrentRepairApproval() {
        return currentRepairApproval;
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
