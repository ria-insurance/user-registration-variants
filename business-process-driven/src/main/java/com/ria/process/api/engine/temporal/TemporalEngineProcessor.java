package com.ria.process.api.engine.temporal;


import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import io.temporal.serviceclient.WorkflowServiceStubs;
import org.springframework.stereotype.Component;

@Component
public class TemporalEngineProcessor {

    public void signalWorkflow(String workflowName, String methodName, String runId, Object input) {
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkflowOptions options = WorkflowOptions.newBuilder().build();
        WorkflowStub workflow = client.newUntypedWorkflowStub(workflowName, options);
        workflow.signal(methodName, input);
        // TODO(abhideep): Run a query here to generate the response
    }

    public  String startWorkflow(String workflowName, Object input) {
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkflowOptions options = WorkflowOptions.newBuilder().build();
        WorkflowStub workflow = client.newUntypedWorkflowStub(workflowName, options);
        WorkflowExecution execution = workflow.start(input);
        return execution.getRunId();
    }
}
