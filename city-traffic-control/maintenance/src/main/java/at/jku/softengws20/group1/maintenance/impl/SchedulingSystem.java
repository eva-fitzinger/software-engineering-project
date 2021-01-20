package at.jku.softengws20.group1.maintenance.impl;

import at.jku.softengws20.group1.maintenance.dummy.data.DummyRegularRepair;
import at.jku.softengws20.group1.maintenance.restservice.ControlSystemService_Maintenance;
import at.jku.softengws20.group1.shared.impl.model.Timeslot;
import at.jku.softengws20.group1.shared.impl.model.MaintenanceRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import static at.jku.softengws20.group1.maintenance.impl.MaintenanceConstants.NUMBER_OF_TIME_SLOTS;

/**
 * This class is handles the scheduling of Repairs.
 */
@Service
public class SchedulingSystem {

    @Autowired
    private CityMapService cityMapService;

    private final ControlSystemService_Maintenance controlSystemServiceMaintenance = new ControlSystemService_Maintenance();


    private final List<Repair> schedule;
    private final List<RegularRepair> notApprovedRegularRepairs;

    public SchedulingSystem() {
        schedule = new ArrayList<>();
        notApprovedRegularRepairs = new ArrayList<>();
    }

    public List<Repair> getSchedule() {
        return schedule;
    }

    /**
     * Adds a random  <a href="#{@link}">{@link RegularRepair}</a> to the schedule.
     * Mostly used for the dummy implementation to demonstrate the System.
     */
    public void addRegularRepair() {
        RegularRepair regularRepair = DummyRegularRepair.getRegularRepair();
        at.jku.softengws20.group1.shared.impl.model.Timeslot[] timeslots = findAvailableTimeslots(regularRepair);
        regularRepair.setTimeslot(timeslots);
        addRegularRepair(regularRepair);
    }

    /**
     * Returns an array of  <a href="#{@link}">{@link Timeslot}</a>.
     * The timeslots are calculated in a way so that there can only be one
     * <a href="#{@link}">{@link RegularRepair}</a> at a time!
     * Mostly used for the dummy implementation to demonstrate the System.
     *
     * @param regularRepair The <a href="#{@link}">{@link RegularRepair}</a> for which a
     *                      <a href="#{@link}">{@link Timeslot}</a> has to be found.
     * @return an array of available  <a href="#{@link}">{@link Timeslot}</a>
     */
    private at.jku.softengws20.group1.shared.impl.model.Timeslot[] findAvailableTimeslots(RegularRepair regularRepair) {
        at.jku.softengws20.group1.shared.impl.model.Timeslot timeslot;
        at.jku.softengws20.group1.shared.impl.model.Timeslot[] availableTimeslots = new at.jku.softengws20.group1.shared.impl.model.Timeslot[3];

        int i = 0;
        while (i < NUMBER_OF_TIME_SLOTS) {
            timeslot = (Timeslot) DummyRegularRepair.getDummyTimeSlot(regularRepair);

            if (schedule.size() == 0) {
                availableTimeslots[i] = timeslot;
                i++;
            } else {
                for (int j = 0; j < schedule.size() && timeslot.getTo().before(schedule.get(j).getTo()); j++) { //as long as the timeSlot end is before the end of the current schedule
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

    /**
     * Adds an existing <a href="#{@link}">{@link RegularRepair}</a> to the schedule.
     * If the Maintenance System had a GUI, this is the method that would be used to connect it
     * to the GUI. The method requests a road closing at the ControlSystem.
     *
     * @param regularRepair an already created <a href="#{@link}">{@link RegularRepair}</a>.
     */

    public void addRegularRepair(@NonNull RegularRepair regularRepair) {
         Random rand = new Random();
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

        // request permission to schedule and close road from ControlSystem
        controlSystemServiceMaintenance.requestRoadClosing(maintenanceRequest);
    }

    /**
     * Schedules the first possible Timeslot in the schedule and calls to print the schedule
     * in the Terminal.
     *
     * @param approvedMaintenanceRequest an approved <a href="#{@link}">{@link MaintenanceRequest}</a>
     *                                   usually received from the ControlSystem.
     */
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

        if (timeslot == null || timeslot.getFrom() == null || timeslot.getTo() == null) {
            return;
        }
        regularRepair.setTime(timeslot.getFrom(), timeslot.getTo());

        //safe first possible Timeslot in schedule
        schedule.add(regularRepair);

        printSchedule();
    }

    public void printSchedule() {
        System.out.println("-------- Time Table for this Year ---------");
        schedule.sort((r1, r2) -> {
            if (r1 == r2) {
                return 0;
            }
            return r1.getFrom().before(r2.getFrom()) ? -1 : 1;
        });
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

        for (Repair repair : schedule) {
            System.out.println(formatter.format(repair.getFrom()) + "-"
                    + formatter.format(repair.getTo()) + ":" +
                    repair.getRepairId());
        }
    }

    /**
     * Method receives an <a href="#{@link}">{@link EmergencyRepair}</a> and reschedules the schedule
     * in case there would be an overlap.
     *
     * @param emergencyRepair already created <a href="#{@link}">{@link EmergencyRepair}</a>
     *                        its duration is used to reschedule the schedule if necessary
     */

    public void addEmergencyRepair(@NonNull EmergencyRepair emergencyRepair) {
        //add right away
        System.out.println("Maintenance:: Emergency Repair with: " + emergencyRepair.getNrVehiclesNeeded() + " vehicle(s)");
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
