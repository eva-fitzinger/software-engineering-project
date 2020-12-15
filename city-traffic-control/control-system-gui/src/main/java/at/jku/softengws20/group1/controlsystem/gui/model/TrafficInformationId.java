package at.jku.softengws20.group1.controlsystem.gui.model;

import java.util.Arrays;
import java.util.Objects;

public class TrafficInformationId {
    private final String[] ids = new String[2];

    public TrafficInformationId(String crossingId, String roadSegmentId) {
        ids[0] = crossingId;
        ids[1] = roadSegmentId;
    }

    public String getCrossingId() {
        return ids[0];
    }

    public String getRoadSegmentId() {
        return ids[1];
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(ids);
    }

    @Override
    public boolean equals(Object other) {
        TrafficInformationId otherTiId = (TrafficInformationId)other;
        if (other == null) {
            return false;
        }
        return Objects.equals(this.getCrossingId(), otherTiId.getCrossingId())
                && Objects.equals(this.getRoadSegmentId(), otherTiId.getRoadSegmentId());
    }
}
