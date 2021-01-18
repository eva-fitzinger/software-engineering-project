package at.jku.softengws20.group1.shared.impl.model;

import java.util.Arrays;

public enum RoadType {
    MOTORWAY("motorway", 130, 1),
    TRUNK("trunk", 130, 1),
    PRIMARY("primary", 100, 1),
    SECONDARY("secondary", 100, 1),
    TERTIARY("tertiary", 100, 1),
    UNCLASSIFIED("unclassified", 100, 1),
    RESIDENTIAL("residential", 100, 1),
    MOTORWAY_LINK("motorway_link", 130, 1),
    TRUNK_LINK("trunk_link", 130, 1),
    PRIMARY_LINK("primary_link", 100, 1),
    SECONDARY_LINK("secondary_link", 100, 1),
    TERTIARY_LINK("tertiary_link", 100, 1),
    LIVING_STREET("living_street", 30, 1);

    private final String osmName;
    private final int maxSpeed;
    private final boolean isLink;
    private final double greenLightPriorityFactor;


    RoadType(String osmName, int maxSpeed, double greenLightPriorityFactor) {
        this.osmName = osmName;
        this.maxSpeed = maxSpeed;
        this.greenLightPriorityFactor = greenLightPriorityFactor;
        this.isLink = osmName.endsWith("_link");
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public boolean isLink() {
        return isLink;
    }

    public double getGreenLightPriorityFactor() { return greenLightPriorityFactor; }

    public static RoadType fromOSMName(String name) {
        return Arrays.stream(RoadType.values()).filter(r -> r.osmName.equals(name)).findAny().orElse(null);
    }
}
