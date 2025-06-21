package utms.model;

public class Lecturer extends Users {
    public Lecturer(String username) {
        super(username);
    }

    @Override
    public void requestTransport() {
        System.out.println("Lecturer requesting transport...");
    }
}