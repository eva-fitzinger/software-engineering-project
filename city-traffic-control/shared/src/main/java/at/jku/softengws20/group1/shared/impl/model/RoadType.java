package at.jku.softengws20.group1.shared.impl.model;

import java.util.Arrays;

public enum RoadType {
    MOTORWAY("motorway"),
    TRUNK("trunk"),
    PRIMARY("primary"),
    SECONDARY("secondary"),
    TERTIARY("tertiary"),
    UNCLASSIFIED("unclassified"),
    RESIDENTIAL("residential"),
    MOTORWAY_LINK("motorway_link"),
    LIVING_STREET("living_street");

    private final String osmName;

    RoadType(String osmName) {
        this.osmName = osmName;
    }

    public static RoadType fromOSMName(String name) {
        return Arrays.stream(RoadType.values()).filter(r -> r.osmName.equals(name)).findAny().orElse(null);
    }
}
