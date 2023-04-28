package com.ria.experiments.businessprocessdriven.registration.activities;

import com.ria.experiments.businessprocessdriven.registration.dtos.UnverifiedRegistrationEmail;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface UnverifiedEmailRecording {
    @ActivityMethod
    UnverifiedRegistrationEmail acceptUnverifiedEmail(UnverifiedRegistrationEmail email);
}
