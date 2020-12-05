package at.jku.softengws20.group1.shared.impl.model;

public class Road implements at.jku.softengws20.group1.shared.controlsystem.Road {

    private String id;
    private String name;
    private String number;

    public Road() {}

    public Road(String id, String name, String number) {
        this.id = id;
        this.name = name;
        this.number = number;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getNumber() {
        return number;
    }
}
