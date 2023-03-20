package com.ria.process.api.model;

public class VerifyOtpRequest {
    private final String userId;
    private final String emailAddress;
    private final String otp;

    public VerifyOtpRequest(String userId, String emailAddress, String otp) {
        this.userId = userId;
        this.emailAddress = emailAddress;
        this.otp = otp;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getOtp() {
        return otp;
    }
}
