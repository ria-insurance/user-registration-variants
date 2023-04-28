package com.ria.experiments.businessprocessdriven.registration.client;

import com.ria.experiments.businessprocessdriven.registration.Shared;
import com.ria.experiments.businessprocessdriven.registration.workflow.UserRegistration;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Slf4j
public class StartUserRegistration {
    public static void main(String[] args) {
        // WorkflowServiceStubs is a gRPC stubs wrapper that talks to the local Docker instance of the Temporal server.
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setTaskQueue(Shared.USER_REGISTRATION_TASK_QUEUE)
                // A WorkflowId prevents this it from having duplicate instances, remove it to duplicate.
                .setWorkflowId("user-registration-workflow")
                .build();
        // WorkflowClient can be used to start, signal, query, cancel, and terminate Workflows.
        WorkflowClient client = WorkflowClient.newInstance(service);
        // WorkflowStubs enable calls to methods as if the Workflow object is local, but actually perform an RPC.
        var workflow = client.newWorkflowStub(UserRegistration.class, options);
        String trackingId = UUID.randomUUID().toString();
        // Asynchronous execution. This process will exit after making this call.
        WorkflowExecution we = WorkflowClient.start(workflow::startUserRegistration, trackingId);
        log.info("User ID {} is being registered", trackingId);
        log.info("WorkflowID: {} RunID: {}", we.getWorkflowId(), we.getRunId());
        System.exit(0);
    }
}
