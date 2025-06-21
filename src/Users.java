
package utms.model;

public abstract class Users {
    protected String username;

    public Users(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public abstract void requestTransport();
}