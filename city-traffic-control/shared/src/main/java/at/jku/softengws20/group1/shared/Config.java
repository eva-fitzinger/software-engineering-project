package at.jku.softengws20.group1.shared;

/*
Use for all constants parameter in the project.

user with: import at.jku.softengws20.group1.shared.Config;
 */

public class Config {
    //General-----------------------------------------------------------------------------------------------------------
    public static final float REAL_TIME_FACTOR = 3;       //0 = realtime ?? (wieso nicht 1?)
    public static final int MAX_CARS = 1000;


    //Detection---------------------------------------------------------------------------------------------------------
    public static final float MINUTES_FOR_FULL_TRAFFIC_LIGHT_RUN = 1;
    public static final String STANDARD_TEST_INFO_SIGN = "good ride";


    //Maintenance-------------------------------------------------------------------------------------------------------
    public static final int MAX_MAINTENANCE_VEHICLES = 10;
    public static final int MAX_EMPLOYEES = 50;
}
