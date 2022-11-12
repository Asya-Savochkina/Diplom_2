package model;

public class ChangeUserDataRequest {
    private String email;
    private String name;
    private String authorization;

    public ChangeUserDataRequest() {
    }

    public ChangeUserDataRequest(String email, String name, String authorization) {
        this.email = email;
        this.name = name;
        this.authorization = authorization;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }
}
