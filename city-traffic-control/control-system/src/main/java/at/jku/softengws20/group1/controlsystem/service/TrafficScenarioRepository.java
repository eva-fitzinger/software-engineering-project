package at.jku.softengws20.group1.controlsystem.service;

import at.jku.softengws20.group1.controlsystem.internal.RecurringScenario;
import at.jku.softengws20.group1.controlsystem.internal.Schedule;
import at.jku.softengws20.group1.controlsystem.internal.TrafficScenario;
import at.jku.softengws20.group1.shared.impl.model.RoadSegment;
import at.jku.softengws20.group1.shared.impl.model.Timeslot;
import at.jku.softengws20.group1.shared.impl.model.TrafficLightRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@Repository
public class TrafficScenarioRepository {

    private RecurringScenario[] trafficScenario = new RecurringScenario[3];

    public TrafficScenarioRepository() {
        loadTestData();
    }

    private void loadTestData() {
        MapRepository mapRepository = new MapRepository();
        mapRepository.loadMap();
        SimpleDateFormat formatter = new SimpleDateFormat("kk:mm:ss", Locale.GERMAN);
        formatter.setTimeZone(TimeZone.getTimeZone("CET"));

        trafficScenario[0] = new RecurringScenario("0", "Default", new Schedule(83000, 150000, new int[]{2,3,4,5,6,7}), true); // Mo - Sa
        trafficScenario[1] = new RecurringScenario("1", "Rushhour", new Schedule(060000, 83000, new int[]{2,3,4,5,6,7}), false); // Mo - Sa
        trafficScenario[2] = new RecurringScenario("2", "Sunday", new Schedule(0, 240000, new int[]{1}), false); // So

        Random rand = new Random();

        for (int i = 0; i < trafficScenario.length; i++) {
            for (RoadSegment rs:mapRepository.getRoadNetwork().getRoadSegments()) {
                trafficScenario[i].addRule(new TrafficLightRule(rs.getCrossingBId(),rs.getId(),rand.nextDouble()));
            }
        }
    }

    public TrafficScenario[] getTrafficScenarios () {
        return trafficScenario;
    }

    public TrafficScenario[] getEnabledTrafficScenarios() {
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        Date now = new Date();
        int nowInt = now.getHours() * 10000 + now.getMinutes() * 100 + now.getSeconds();

        List<TrafficScenario> result = new ArrayList<>();

        for (int i = 0; i < trafficScenario.length; i++) {

            if (trafficScenario[i].getSchedule().containsTime(nowInt, dayOfWeek)) {
                result.add(trafficScenario[i]);
            }
        }

        //return default scenario of result is empty
        if (result.size() == 0) {
            for (int i = 0; i < trafficScenario.length; i++) {
                // add default scenarios
                if (trafficScenario[i].getDefaultScenario()) {
                    result.add(trafficScenario[i]);
                }
            }
        }

        return result.toArray(new TrafficScenario[0]);
    }

    public TrafficLightRule[] getEnabledTrafficLightRules() {
        List<TrafficLightRule> result = new ArrayList<>();
        for (TrafficScenario ts :  getEnabledTrafficScenarios()
             ) {
            result.addAll(ts.getTrafficLightRules());
        }
        return result.toArray(new TrafficLightRule[0]);
    }



}
