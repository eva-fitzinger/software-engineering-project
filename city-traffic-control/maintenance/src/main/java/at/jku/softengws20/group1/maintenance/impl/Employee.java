package at.jku.softengws20.group1.maintenance.impl;

public class Employee {

    private final String id;
    private String name;
    private String assignedToCar;

    public Employee(String id) {
        this.id = id;
        this.name = "Lisa Muster";
    }

    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAssignedToCar() {
        return assignedToCar;
    }

    public void setAssignedToCar(String assignedToCar) {
        this.assignedToCar = assignedToCar;
    }
}
