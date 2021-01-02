package at.jku.softengws20.group1.maintenance.impl;

import at.jku.softengws20.group1.maintenance.dummy.data.DummyRegularRepair;
import at.jku.softengws20.group1.maintenance.restservice.ControlSystemService_Maintenance;
import at.jku.softengws20.group1.shared.controlsystem.Timeslot;
import at.jku.softengws20.group1.shared.impl.model.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;


public class SchedulingSystem { // TODO: enhance in Milestone 3.2
    private final int MAX_WEEKS = 52;
    private final int MAX_DAYS_OF_WEEK = 6;
    private final int MAX_HOURS = 8;

    private static RegularRepair currentRepairApproval;
    private int weekNr;
    private int dayOfWeek;
    private int hourOfDay;
    private ControlSystemService_Maintenance controlSystemServiceMaintenance = new ControlSystemService_Maintenance();
    private GregorianCalendar calendar;

    public List<Repair> getSchedule() {
        return schedule;
    }

    private List<Repair> schedule;

    public SchedulingSystem(int year, int month, int dayOfMonth, int hourOfDay) {
//        this.calendar = new GregorianCalendar(year, month, dayOfMonth, hourOfDay, 0);
//
//        this.weekNr = calendar.get(Calendar.WEEK_OF_YEAR);
//        this.calendar.set(year, month, dayOfMonth, hourOfDay, 0);
//        this.dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
//        this.calendar.set(year, month, dayOfMonth, hourOfDay, 0);
//        this.hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
//        this.calendar.set(year, month, dayOfMonth, hourOfDay, 0);
//        this.schedule = new String[MAX_WEEKS - weekNr][MAX_DAYS_OF_WEEK][MAX_HOURS][2];
        initCalendar();
    }

    private void initCalendar() { // Todo maybe change to unittest instead of hardcoded stuff
//        for (int day = 0; day < schedule[weekNr].length; day++) {
//            for (int hour = 0; hour < schedule[weekNr][day].length; hour++) {
//                schedule[weekNr][day][hour][0] = "Week Nr.:" + weekNr + 1 + "; Day of Week: " + (day + 1) + "; working hour: " + (hour + 1);
//            }
//        }
        Random rand = new Random();
        for (int i = 0; i < 5; i++) {
            addRegularRepair(); // employees needed
        }
    }

    public void printSchedule() {
        System.out.println("-------- Time Table for this Week ---------");
//        for (int day = 0; day < schedule[weekNr].length; day++) {
//            for (int hour = 0; hour < schedule[weekNr][day].length; hour++) {
//                System.out.print(schedule[weekNr][day][hour][0] + ": ");
//                System.out.println(schedule[weekNr][day][hour][1] == null ? "no repair" : schedule[weekNr][day][hour][1]);
//            }
//        }
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

        for (int i = 0; i < 3;) {
            //TODO delivers a timeslot in the bounds of the working hours
            timeslot = DummyRegularRepair.getDummyTimeSlot(regularRepair);

            for (int j = 0; schedule.get(j) != null && timeslot.getTo().before(schedule.get(j).getTo()); j++) { //as long as the timeSlot end is before the end of the current schedule
                //if at beginning
                if(timeslot.getTo().before(schedule.get(0).getFrom())){
                    approvedTimeslots[i] = timeslot;
                    i++;
                }
                //if at end
                if(schedule.get(j+1) == null){
                    approvedTimeslots[i] = timeslot;
                    i++;
                }

                //if between to scheduled dates
                if (schedule.get(j).getTo().before(timeslot.getFrom()) && timeslot.getTo().before(schedule.get(j+1).getFrom())) {
                    approvedTimeslots[i] = timeslot;
                    i++;
                }
            }
        }
        return approvedTimeslots;
    }

    public void addRegularRepair(RegularRepair regularRepair) {//TODO comment on/out
        currentRepairApproval = regularRepair;
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


    public void addEmergencyRepair() { //TODO: in Milestone 3.2
        //add right away
//        EmergencyRepair emergencyRepair = new EmergencyRepair(...);
        //reschedule rest?
    }
}
