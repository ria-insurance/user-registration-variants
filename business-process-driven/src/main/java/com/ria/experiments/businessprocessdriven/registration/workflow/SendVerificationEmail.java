package com.ria.experiments.businessprocessdriven.registration.workflow;

import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface SendVerificationEmail {
    @WorkflowMethod
    String sendVerificationEmail(String emailAddress);
    @SignalMethod
    void emailVerified(String verifiedEmail);
}
