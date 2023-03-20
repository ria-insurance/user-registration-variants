package com.ria.process.api.model;

public class VerifyOtpResponse {
    private final boolean requestStatus;
    private final String errorCode;

    public VerifyOtpResponse(boolean requestStatus, String errorCode) {
        this.requestStatus = requestStatus;
        this.errorCode = errorCode;
    }

    public static VerifyOtpResponse success() {
        return new VerifyOtpResponse(true, null);
    }

    public boolean isRequestStatus() {
        return requestStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
