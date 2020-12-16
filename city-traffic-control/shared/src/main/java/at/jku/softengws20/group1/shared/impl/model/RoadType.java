package at.jku.softengws20.group1.shared.impl.model;

import java.util.Arrays;

public enum RoadType {
    MOTORWAY("motorway", 130),
    TRUNK("trunk", 130),
    PRIMARY("primary", 100),
    SECONDARY("secondary", 100),
    TERTIARY("tertiary", 100),
    UNCLASSIFIED("unclassified", 100),
    RESIDENTIAL("residential", 100),
    MOTORWAY_LINK("motorway_link", 130),
    TRUNK_LINK("trunk_link", 130),
    PRIMARY_LINK("primary_link", 100),
    SECONDARY_LINK("secondary_link", 100),
    TERTIARY_LINK("tertiary_link", 100),
    LIVING_STREET("living_street", 30);

    private final String osmName;
    private final int maxSpeed;
    private final boolean isLink;


    RoadType(String osmName, int maxSpeed) {
        this.osmName = osmName;
        this.maxSpeed = maxSpeed;
        this.isLink = osmName.endsWith("_link");
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public boolean isLink() {
        return isLink;
    }

    public static RoadType fromOSMName(String name) {
        return Arrays.stream(RoadType.values()).filter(r -> r.osmName.equals(name)).findAny().orElse(null);
    }
}
