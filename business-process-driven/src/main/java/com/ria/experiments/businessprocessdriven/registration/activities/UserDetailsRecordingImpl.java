package com.ria.experiments.businessprocessdriven.registration.activities;

import com.ria.experiments.businessprocessdriven.registration.dtos.UserContactDetails;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserDetailsRecordingImpl implements UserDetailsRecording {
    @Override
    public UserContactDetails recordUserDetails(UserContactDetails userDetails) {
        log.info("User details: {}", userDetails);
        return userDetails;
    }
}
