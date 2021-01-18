package at.jku.softengws20.group1.controlsystem.service;

import at.jku.softengws20.group1.shared.impl.model.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Repository
public class MapRepository {

    private RoadNetwork roadNetwork;

    private void loadJsonMap() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.getFactory().configure(JsonWriteFeature.ESCAPE_NON_ASCII.mappedFeature(), true);
        try (JsonParser parser = mapper.createParser(getClass().getClassLoader().getResourceAsStream("linz.json"))) {
            roadNetwork = parser.readValueAs(RoadNetwork.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    public void loadMap() {
        loadJsonMap();
        //roadNetwork = TestMap.loadDummyMap();
    }

    public RoadNetwork getRoadNetwork() {
        return roadNetwork;
    }

    public RoadSegment getRoadSegment(String roadSegmentId) {
        Optional<RoadSegment> roadSegment = Arrays.stream(roadNetwork.getRoadSegments()).filter(r -> r.getId().equals(roadSegmentId)).findAny();
        return roadSegment.orElse(null);
    }

}
