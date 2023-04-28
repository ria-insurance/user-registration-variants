package com.ria.experiments.businessprocessdriven.registration.activities;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MarkRegistrationIntentImpl implements MarkRegistrationIntent {
    @Override
    public String recordRegistrationIntent(String trackingId) {
        log.info("Recording tracking ID: {}", trackingId);
        log.warn("Navigating to landing page /landing");
        return trackingId;
    }
}
