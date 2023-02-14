package com.ria.experiments.businessprocessdriven.registration.workflow;

import com.ria.experiments.businessprocessdriven.registration.activities.UnverifiedEmailRecording;
import com.ria.experiments.businessprocessdriven.registration.activities.MarkRegistrationIntent;
import com.ria.experiments.businessprocessdriven.registration.activities.UserDetailsRecording;
import com.ria.experiments.businessprocessdriven.registration.dtos.UnverifiedRegistrationEmail;
import com.ria.experiments.businessprocessdriven.registration.dtos.UserContactDetails;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;

@Slf4j
public class UserRegistrationWorkflow implements UserRegistration {
    private static final String USER_REGISTRATION = "User Registration";
    private final RetryOptions retryoptions = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(1))
            .setMaximumInterval(Duration.ofSeconds(100))
            .setBackoffCoefficient(2)
            .setMaximumAttempts(500)
            .build();

    private final ActivityOptions defaultActivityOptions = ActivityOptions.newBuilder()
            // Timeout options specify when to automatically timeout Activities if the process is taking too long.
            .setStartToCloseTimeout(Duration.ofSeconds(5))
            // Optionally provide customized RetryOptions.
            // Temporal retries failures by default, this is simply an example.
            .setRetryOptions(retryoptions)
            .build();
    private final Map<String, ActivityOptions> perActivityMethodOptions = Map.of(USER_REGISTRATION,
            ActivityOptions.newBuilder().setHeartbeatTimeout(Duration.ofSeconds(5)).build());
    // ActivityStubs enable calls to Activities as if they are local methods, but actually perform an RPC.
    private final MarkRegistrationIntent markRegistrationIntent = Workflow.newActivityStub(MarkRegistrationIntent.class, defaultActivityOptions, perActivityMethodOptions);
    private final UserDetailsRecording userDetailsRecording = Workflow.newActivityStub(UserDetailsRecording.class, defaultActivityOptions);
    private final UnverifiedEmailRecording unverifiedEmailRecording = Workflow.newActivityStub(UnverifiedEmailRecording.class, defaultActivityOptions);

    @Override
    public String startUserRegistration(String trackingId) {
        log.warn("User registration started: {}", trackingId);
        markRegistrationIntent.recordRegistrationIntent(trackingId);
        log.warn("Calling user details");
        userDetailsRecording.recordUserDetails(new UserContactDetails("some first name", "some last name", "+91-990011199"));
        unverifiedEmailRecording.acceptUnverifiedEmail(new UnverifiedRegistrationEmail(UUID.fromString(trackingId), "a@test.com"));

        return trackingId;
    }

}
