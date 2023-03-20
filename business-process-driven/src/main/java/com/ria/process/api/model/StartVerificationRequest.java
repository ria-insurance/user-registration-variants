package com.ria.process.api.model;

public class StartVerificationRequest {
    private final String userId;
    private final String emailAddress;

    public StartVerificationRequest(String userId, String emailAddress) {
        this.userId = userId;
        this.emailAddress = emailAddress;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
