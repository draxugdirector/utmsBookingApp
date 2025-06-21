package utms.model;

public abstract class Van extends Vehicle {
    public Van() {
        super("Van", 14);
    }

    public void schedule(String time) {
        System.out.println("Van scheduled at " + time);
    }
}