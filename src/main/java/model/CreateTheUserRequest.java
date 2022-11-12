package model;

import org.apache.commons.lang3.RandomStringUtils;

public class CreateTheUserRequest {
    private String email;
    private String password;
    private String name;

    public CreateTheUserRequest() {
    }

    public CreateTheUserRequest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private static final String MAIL = RandomStringUtils.randomAlphanumeric(5) + "@example.ru";
    private static final String PASSWORD = "qwerty";
    private static final String NAME = "mike";

    public static CreateTheUserRequest getUserAllRequiredField() {
        CreateTheUserRequest createTheUserRequest = new CreateTheUserRequest();
        createTheUserRequest.setEmail(MAIL);
        createTheUserRequest.setPassword(PASSWORD);
        createTheUserRequest.setName(NAME);
        return createTheUserRequest;
    }

    public static CreateTheUserRequest getUserWithoutMail() {
        CreateTheUserRequest createTheUserRequest = new CreateTheUserRequest();
        createTheUserRequest.setPassword(PASSWORD);
        createTheUserRequest.setName(NAME);
        return createTheUserRequest;
    }

    public static CreateTheUserRequest getUserWithoutPassword() {
        CreateTheUserRequest createTheUserRequest = new CreateTheUserRequest();
        createTheUserRequest.setEmail(MAIL);
        createTheUserRequest.setName(NAME);
        return createTheUserRequest;
    }

    public static CreateTheUserRequest getUserWithoutName() {
        CreateTheUserRequest createTheUserRequest = new CreateTheUserRequest();
        createTheUserRequest.setEmail(MAIL);
        createTheUserRequest.setPassword(PASSWORD);
        return createTheUserRequest;
    }

}
