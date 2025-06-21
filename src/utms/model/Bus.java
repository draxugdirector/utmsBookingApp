package utms.model;

public abstract class Bus extends Vehicle {
    public Bus() {
        super("Bus", 40);
    }

    public void schedule(String time) {
        System.out.println("Bus scheduled at " + time);
    }
}