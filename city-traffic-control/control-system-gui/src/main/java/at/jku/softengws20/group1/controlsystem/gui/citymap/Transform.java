package at.jku.softengws20.group1.controlsystem.gui.citymap;

import at.jku.softengws20.group1.shared.impl.model.Position;

public interface Transform {
    Position transform(Position position);
}
