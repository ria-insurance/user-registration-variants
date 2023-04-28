package com.ria.experiments.businessprocessdriven.registration.workflow;

import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class SendVerificationEmailWorkflow implements SendVerificationEmail {
    String verifiedEmail = null;
    @Override
    public String sendVerificationEmail(String emailAddress) {
        log.warn("Sending verification email to: {}", emailAddress);
        while (true) {
            Workflow.await(() -> Objects.nonNull(verifiedEmail));
            return verifiedEmail;
        }
    }

    @Override
    public void emailVerified(String verifiedEmail) {
        this.verifiedEmail = verifiedEmail;
    }


}
