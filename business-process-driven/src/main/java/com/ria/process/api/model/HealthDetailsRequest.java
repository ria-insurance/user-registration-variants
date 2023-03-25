package com.ria.process.api.model;

public record HealthDetailsRequest(String userId, Integer age, Integer weight, Integer height, String action) {
}

