package com.ria.experiments.businessprocessdriven.registration.client;

import com.ria.experiments.businessprocessdriven.registration.Shared;
import com.ria.experiments.businessprocessdriven.registration.workflow.SendVerificationEmail;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;

public class ClickOnVerified {
    public static void main(String[] args) {
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setTaskQueue(Shared.USER_REGISTRATION_TASK_QUEUE)
                // A WorkflowId prevents this it from having duplicate instances, remove it to duplicate.
                .setWorkflowId("user-registration-workflow")
                .build();
        // WorkflowClient can be used to start, signal, query, cancel, and terminate Workflows.
        WorkflowClient client = WorkflowClient.newInstance(service);
        // WorkflowStubs enable calls to methods as if the Workflow object is local, but actually perform an RPC.
        var workflow = client.newWorkflowStub(SendVerificationEmail.class, options);
        // start the Workflow
        WorkflowClient.start(workflow::sendVerificationEmail, "a@test.com");
        workflow.emailVerified("a@test.com");
    }
}
