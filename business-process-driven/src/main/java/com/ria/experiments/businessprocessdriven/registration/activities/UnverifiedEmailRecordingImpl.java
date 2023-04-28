package com.ria.experiments.businessprocessdriven.registration.activities;

import com.ria.experiments.businessprocessdriven.registration.dtos.UnverifiedRegistrationEmail;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UnverifiedEmailRecordingImpl implements UnverifiedEmailRecording {
    @Override
    public UnverifiedRegistrationEmail acceptUnverifiedEmail(UnverifiedRegistrationEmail email) {
        log.info("Unverified email: {}", email);
        return email;
    }
}
