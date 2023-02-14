package com.ria.experiments.businessprocessdriven.registration.activities;

import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface MarkRegistrationIntent {
    @ActivityMethod
    String recordRegistrationIntent(String trackingId);
}
