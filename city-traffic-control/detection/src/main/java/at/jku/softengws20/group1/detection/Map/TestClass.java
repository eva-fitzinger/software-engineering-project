package at.jku.softengws20.group1.detection.Map;
import at.jku.softengws20.group1.shared.impl.model.RoadNetwork;

import javax.accessibility.AccessibleRelation;

public class TestClass {
    private static CityMap cityMap = new CityMap();

    public static void main(String[] args) {
        //createCityMapTest();
        boolean test = true;

        System.out.printf("TrafficLight:%s StreetSeg:%d GREEN %b", "1est", 1, test);
    }

    public CityMap createCityMap(RoadNetwork roadNetwork) {
        for (int i = 0; i < roadNetwork.getRoadSegments().length ; i++) {
            cityMap.putStreet(roadNetwork.getRoadSegments()[i].getId());
        }

        for (int i = 0; i < roadNetwork.getCrossings().length; i++) {
            cityMap.putCrossroad(roadNetwork.getCrossings()[i].getId());
            for (int j = 0; j < roadNetwork.getCrossings()[i].getRoadSegmentIds().length; j++) {
                cityMap.putStreetToCrossroad(roadNetwork.getCrossings()[i].getId() ,roadNetwork.getCrossings()[i].getRoadSegmentIds()[j]);
            }
        }

        cityMap.start();
        return cityMap;
    }

    private static void  createCityMapTest() {
        cityMap.putStreet("1");
        cityMap.putStreet("2");
        cityMap.putStreet("3");
        cityMap.putStreet("4");
        cityMap.putStreet("5");
        cityMap.putStreet("6");
        cityMap.putStreet("7");

        cityMap.putCrossroad("1");
        cityMap.putCrossroad("2");
        cityMap.putCrossroad("3");
        cityMap.putCrossroad("4");
        cityMap.putCrossroad("5");

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

        cityMap.start();
    }




}
