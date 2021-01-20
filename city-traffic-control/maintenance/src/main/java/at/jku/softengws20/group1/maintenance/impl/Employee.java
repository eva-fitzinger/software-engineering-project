package at.jku.softengws20.group1.maintenance.impl;

/**
 * The <a href="#{@link}">{@link Employee}</a> class exists for completion. The number of Employees is considered in order to only
 * send available employees. If time would allow an Employee app could be built which connects to the
 * employees in this class.
 */

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
