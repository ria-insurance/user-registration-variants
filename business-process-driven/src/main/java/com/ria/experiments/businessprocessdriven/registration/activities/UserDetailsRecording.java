package com.ria.experiments.businessprocessdriven.registration.activities;

import com.ria.experiments.businessprocessdriven.registration.dtos.UserContactDetails;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface UserDetailsRecording {
    @ActivityMethod
    UserContactDetails recordUserDetails(UserContactDetails userDetails);
}
