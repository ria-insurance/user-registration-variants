package com.ria.process.api.model;

public class StartVerificationResponse {
    private final boolean requestStatus;
    private final String errorCode;

    public StartVerificationResponse(boolean requestStatus, String errorCode) {
        this.requestStatus = requestStatus;
        this.errorCode = errorCode;
    }

    public static StartVerificationResponse success() {
        return new StartVerificationResponse(true, null);
    }

    public boolean isRequestStatus() {
        return requestStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
