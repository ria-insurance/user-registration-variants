package com.ria.process.engine;

import com.ria.experiments.businessprocessdriven.registration.activities.UserDetailsRecording;
import com.ria.experiments.businessprocessdriven.registration.dtos.UserContactDetails;
import com.ria.process.ExecutionContext;
import com.ria.process.task.PageTaskID;
import com.ria.process.task.TaskExecutor;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;

public class TemporalWorkflowEngine {

    public static TaskExecutor getTaskExecutor(PageTaskID taskID) {
        return new TaskExecutor() {
            @Override
            public String execute(ExecutionContext context) {
                // TODO(abhideep): Introduce mapper to create data from context
                UserContactDetails data = new UserContactDetails("firstname", "lastname",
                        "1234567890");
                return recordUserDetails(data);
            }
        };
    }

    public static String recordUserDetails(UserContactDetails data) {
        // WorkflowServiceStubs is a gRPC stubs wrapper that talks to the local Docker instance of the Temporal server.
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowOptions options = WorkflowOptions.newBuilder()
                // TODO(abhideep): Set Task queues for specific types of workflows
                // .setTaskQueue(Shared.USER_REGISTRATION_TASK_QUEUE)
                // TODO(abhideep) : Set Workflow Id to prevent duplicates
                // .setWorkflowId("user-registration-workflow")
                .build();
        // WorkflowClient can be used to start, signal, query, cancel, and terminate Workflows.
        WorkflowClient client = WorkflowClient.newInstance(service);
        // WorkflowStubs enable calls to methods as if the Workflow object is local, but actually perform an RPC.
        var workflow = client.newWorkflowStub(UserDetailsRecording.class, options);
        // Asynchronous execution. This process will exit after making this call.
        WorkflowExecution execution = WorkflowClient.start(workflow::recordUserDetails, data);
        return execution.getRunId();
    }
}
