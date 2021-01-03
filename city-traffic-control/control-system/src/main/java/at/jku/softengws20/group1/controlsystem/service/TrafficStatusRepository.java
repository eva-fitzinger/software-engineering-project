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
    private ArrayList<RoadSegment> directlyClosedRoadSegments = new ArrayList<>();

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

    public void directlyCloseRoadSegment(RoadSegment roadSegment) {
        if (roadSegment == null) return;
        closeRoadSegment(roadSegment);
        if (!directlyClosedRoadSegments.contains(roadSegment)) {
            directlyClosedRoadSegments.add(roadSegment);
        }
    }

    public void directlyOpenRoadSegment(RoadSegment roadSegment) {
        if (roadSegment == null) return;
        openRoadSegment(roadSegment);
        if (directlyClosedRoadSegments.contains(roadSegment)) {
            directlyClosedRoadSegments.remove(roadSegment);
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

    public RoadSegmentStatus[] getClosedRoadSegments() {
        ArrayList<RoadSegmentStatus> result = new ArrayList<RoadSegmentStatus>();
        for (RoadSegmentStatus roadSegmentStatus : getRoadSegmentStatus()) {
            if (!roadSegmentStatus.isOpen()) {
                result.add(roadSegmentStatus);
            }
        }
        return result.toArray(new RoadSegmentStatus[0]);
    }

    public RoadSegmentStatus[] getClosedRoadSegmentsByMaintenance() {
        ArrayList<RoadSegmentStatus> result = new ArrayList<RoadSegmentStatus>();
        for (RoadSegmentStatus roadSegmentStatus : getRoadSegmentStatus()) {
            if (!roadSegmentStatus.isOpen()) {
                RoadSegment roadSegment = mapRepository.getRoadSegment(roadSegmentStatus.getRoadSegmentId());
                if (!directlyClosedRoadSegments.contains(roadSegment)) {
                    result.add(roadSegmentStatus);
                }
            }
        }
        return result.toArray(new RoadSegmentStatus[0]);
    }

}
