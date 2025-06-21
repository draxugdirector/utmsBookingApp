package utms.model;

public class TransportOfficer extends Users {
    public TransportOfficer(String username) {
        super(username);
    }

    @Override
    public void requestTransport() {
        System.out.println("TransportOfficer requesting transport...");
    }
}