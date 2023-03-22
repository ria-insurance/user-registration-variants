package com.ria.process.api.model;

public record VerifyOtpRequest(String userId, String emailAddress, String otp) {

}
