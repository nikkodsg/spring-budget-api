package com.nikkodasig.springbudgetapi.registration;

public class RegistrationResponse {

    private String confirmationToken;

    public RegistrationResponse(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }
}