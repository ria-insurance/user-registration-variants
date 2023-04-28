package com.ria.experiments.businessprocessdriven.registration.workflow;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;
@WorkflowInterface
public interface UserRegistration {
    @WorkflowMethod
    String startUserRegistration(String trackingId);
}
