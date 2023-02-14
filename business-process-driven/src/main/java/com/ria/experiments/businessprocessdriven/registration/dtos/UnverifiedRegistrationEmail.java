package com.ria.experiments.businessprocessdriven.registration.dtos;

import java.util.UUID;

public record UnverifiedRegistrationEmail(UUID userId, String email) {
}
