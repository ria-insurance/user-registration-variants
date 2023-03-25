package com.ria.process.api.model;

public record ProposalResponse(boolean requestStatus, String errorCode, String pageName){
    public static ProposalResponse success(String pageName) {
        return new ProposalResponse(true, null, pageName);
    }
}
