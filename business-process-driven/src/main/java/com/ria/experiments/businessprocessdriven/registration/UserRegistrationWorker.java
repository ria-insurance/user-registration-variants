package com.ria.experiments.businessprocessdriven.registration;

import com.ria.experiments.businessprocessdriven.registration.activities.UnverifiedEmailRecordingImpl;
import com.ria.experiments.businessprocessdriven.registration.activities.MarkRegistrationIntentImpl;
import com.ria.experiments.businessprocessdriven.registration.activities.UserDetailsRecordingImpl;
import com.ria.experiments.businessprocessdriven.registration.workflow.UserRegistrationWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.worker.Worker;
import io.temporal.worker.WorkerFactory;

public class UserRegistrationWorker {
    public static void main(String[] args) {
        // This gRPC stubs wrapper talks to the local docker instance of the Temporal service.
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);
        // Create a Worker factory that can be used to create Workers that poll specific Task Queues.
        WorkerFactory factory = WorkerFactory.newInstance(client);
        Worker worker = factory.newWorker(Shared.USER_REGISTRATION_TASK_QUEUE);
        // This Worker hosts both Workflow and Activity implementations.
        // Workflows are stateful, so you need to supply a type to create instances.
        worker.registerWorkflowImplementationTypes(UserRegistrationWorkflow.class);
        // Activities are stateless and thread safe, so a shared instance is used.
        worker.registerActivitiesImplementations(new MarkRegistrationIntentImpl(), new UserDetailsRecordingImpl(), new UnverifiedEmailRecordingImpl());
        // Start polling the Task Queue.
        factory.start();
    }
}
