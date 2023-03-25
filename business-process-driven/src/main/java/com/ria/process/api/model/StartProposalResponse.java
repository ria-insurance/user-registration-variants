package com.ria.process.api.model;

public class StartProposalResponse {
    private final boolean requestStatus;
    private final String errorCode;

    public StartProposalResponse(boolean requestStatus, String errorCode) {
        this.requestStatus = requestStatus;
        this.errorCode = errorCode;
    }

    public static StartProposalResponse success() {
        return new StartProposalResponse(true, null);
    }

    public boolean isRequestStatus() {
        return requestStatus;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
