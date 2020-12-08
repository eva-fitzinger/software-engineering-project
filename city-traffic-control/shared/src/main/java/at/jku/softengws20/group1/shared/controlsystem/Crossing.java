package at.jku.softengws20.group1.shared.controlsystem;

import java.util.Collection;

public interface Crossing {
    String getId();
    Position getPosition();
    Collection<String> getRoadSegmentIds();
}
