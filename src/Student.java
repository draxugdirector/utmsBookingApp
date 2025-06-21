package utms.model;

public class Student extends Users {
    public Student(String username) {
        super(username);
    }

    @Override
    public void requestTransport() {
        System.out.println("Student requesting transport...");
    }
}