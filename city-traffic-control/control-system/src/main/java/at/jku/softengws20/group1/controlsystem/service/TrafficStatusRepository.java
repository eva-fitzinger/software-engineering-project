package at.jku.softengws20.group1.controlsystem.service;

import at.jku.softengws20.group1.shared.detection.TrafficLoad;
import at.jku.softengws20.group1.shared.impl.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.stream.Collectors;

@Repository
public class TrafficStatusRepository {
    @Autowired
    private MapRepository mapRepository;
    @Autowired
    private TrafficCapacityRepository trafficCapacityRepository;

    private HashMap<RoadSegment, RoadSegmentStatus> roadSegmentStatusHashMap = new HashMap<RoadSegment, RoadSegmentStatus>();

    public void closeRoadSegment(RoadSegment roadSegment) {
        if (roadSegment == null) return;
        if (roadSegmentStatusHashMap.containsKey(roadSegment)) {
            roadSegmentStatusHashMap.get(roadSegment).close();
        } else {
            roadSegmentStatusHashMap.put(roadSegment, new RoadSegmentStatus(roadSegment.getId(), false));
        }
    }

    public void openRoadSegment(RoadSegment roadSegment) {
        if (roadSegmentStatusHashMap.containsKey(roadSegment)) {
            roadSegmentStatusHashMap.get(roadSegment).open();
        } else {
            roadSegmentStatusHashMap.put(roadSegment, new RoadSegmentStatus(roadSegment.getId(), true));
        }
    }
    public void processTrafficLoad(TrafficLoad[] trafficLoad) {
        for (TrafficLoad t : trafficLoad) {
            RoadSegment roadSegment = mapRepository.getRoadSegment(t.getRoadSegmentId());
            setTrafficLoad(
                    roadSegment,
                    trafficCapacityRepository.getCapacity(roadSegment),
                    t.getCarsWaiting()
            );
        }
    }

    private void setTrafficLoad(RoadSegment roadSegment, double capacity, double carsWaiting) {
        // traffic load can only be calculated of capacity > 0
        if (capacity != 0 ) {
            double trafficLoad = Math.min(carsWaiting / capacity, 1.0);
            if (roadSegmentStatusHashMap.containsKey(roadSegment)) {
                roadSegmentStatusHashMap.get(roadSegment).setTrafficLoad(trafficLoad);
            } else {
                roadSegmentStatusHashMap.put(roadSegment, new RoadSegmentStatus(roadSegment.getId(), trafficLoad));
            }
        }
    }

    public RoadSegmentStatus getRoadSegmentStatus(RoadSegment roadSegment) {
        return roadSegmentStatusHashMap.get(roadSegment);
    }

    public RoadSegmentStatus[] getRoadSegmentStatus() {
        return roadSegmentStatusHashMap.values().toArray(new RoadSegmentStatus[0]);
    }

    public ArrayList<RoadSegmentStatus> getClosedRoadSegments() {
        return new ArrayList<RoadSegmentStatus>(Arrays.stream(getRoadSegmentStatus()).filter(r -> !r.isOpen()).collect(Collectors.toList()));
    }

}
