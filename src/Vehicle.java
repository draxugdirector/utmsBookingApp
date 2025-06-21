package utms.model;

import utms.interfaces.Schedulable;

public abstract class Vehicle implements Schedulable {
    protected String type;
    protected int capacity;

    public Vehicle(String type, int capacity) {
        this.type = type;
        this.capacity = capacity;
    }

    public String getType() {
        return type;
    }

    public int getCapacity() {
        return capacity;
    }
}