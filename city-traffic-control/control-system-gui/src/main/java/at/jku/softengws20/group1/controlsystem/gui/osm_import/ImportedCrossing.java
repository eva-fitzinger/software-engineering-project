package at.jku.softengws20.group1.controlsystem.gui.osm_import;

import at.jku.softengws20.group1.shared.impl.model.Position;

class ImportedCrossing {
    private String id;
    private Position position;

    String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    Position getPosition() {
        return position;
    }

    void setPosition(Position position) {
        this.position = position;
    }
}
