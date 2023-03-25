package com.ria.process.api.model;

public record Response(boolean requestStatus, String errorCode) {
    public static Response success() {
        return new Response(true, null);
    }
}
