/*
package at.jku.softengws20.group1.detection.Map;

import at.jku.softengws20.group1.detection.restservice.DetectionController;
import at.jku.softengws20.group1.shared.detection.TrafficLoad;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TestClass {        //########################################   Test Class ###############################
    private CityMap cityMap;
    private DetectionController det = new DetectionController();

    void init() {
        det.init();
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cityMap = det.getCityMap();
        createCityMapTest();
    }

    @Test
    void testTrafficLoadEmpty() {
        init();
        TrafficLoad[] trafficLoads = det.getTrafficLoad();
        assertEquals(0, trafficLoads.length);
    }



    private void  createCityMapTest() {
        cityMap.putStreet("1", "2");
        cityMap.putStreet("2", "3");
        cityMap.putStreet("3", "4");
        cityMap.putStreet("4", "5");
        cityMap.putStreet("5", "1");
        cityMap.putStreet("6", "3");
        cityMap.putStreet("7", "1");

        cityMap.putCrossroad("1");
        cityMap.putCrossroad("2");
        cityMap.putCrossroad("3");
        cityMap.putCrossroad("4");
        cityMap.putCrossroad("5");


        cityMap.start();
    }
}

    public static void createCityMap(RoadNetwork roadNetwork) {
        //create roads
        for (int i = 0; i < roadNetwork.getRoadSegments().length ; i++) {
            String id = roadNetwork.getRoadSegments()[i].getId();
            String toCrossroad = roadNetwork.getRoadSegments()[i].getCrossingBId();     //road goes from A  to B -> B is incoming to a crossroad
            cityMap.putStreet(id, toCrossroad);
            cityMap.getStreet((id)).getSpeedLimit().setStandardSpeedLimit(roadNetwork.getRoadSegments()[i].getDefaultSpeedLimit()); //set default speed limit
        }

        //create crossroads
        for (int i = 0; i < roadNetwork.getCrossings().length; i++) {
            String id = roadNetwork.getCrossings()[i].getId();
            cityMap.putCrossroad(id);
            for (int j = 0; j < roadNetwork.getCrossings()[i].getRoadSegmentIds().length; j++) {
                cityMap.getCrossroad(id).putStreet(cityMap.getStreet(roadNetwork.getCrossings()[i].getRoadSegmentIds()[j]));
            }
        }

        //start traffic lights (running in thread´s)
        cityMap.start();
    }

        cityMap.putStreetToCrossroad("1","1");
        cityMap.putStreetToCrossroad("1","7");
        cityMap.putStreetToCrossroad("1","5");
        cityMap.putStreetToCrossroad("2","1");
        cityMap.putStreetToCrossroad("2","2");
        cityMap.putStreetToCrossroad("2","4");
        cityMap.putStreetToCrossroad("3","6");
        cityMap.putStreetToCrossroad("3","2");
        cityMap.putStreetToCrossroad("3","7");
        cityMap.putStreetToCrossroad("4","6");
        cityMap.putStreetToCrossroad("4","5");
        cityMap.putStreetToCrossroad("4","3");
        cityMap.putStreetToCrossroad("5","4");
        cityMap.putStreetToCrossroad("5","3");
*/
