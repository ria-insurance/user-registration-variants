package com.ria.process.api.engine.temporal;


import com.ria.process.api.engine.EngineProcessor;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import io.temporal.serviceclient.WorkflowServiceStubs;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TemporalEngineProcessor implements EngineProcessor {


    @Override
    public String startWorkflow(String userId, String workflowIdentifier, Object input) {
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkflowOptions options = WorkflowOptions.newBuilder().build();
        WorkflowStub workflow = client.newUntypedWorkflowStub(workflowIdentifier, options);
        WorkflowExecution execution = workflow.start(input);
        return execution.getRunId();
    }

    @Override
    public void signalWorkflow(String userId, String workflowName, String methodName, String runId, Object input) {
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkflowOptions options = WorkflowOptions.newBuilder().build();
        WorkflowStub workflow = client.newUntypedWorkflowStub(workflowName, options);
        workflow.signal(methodName, input);
    }

    @Override
    public Object executeAndQuery(String userId, String workflowName, String methodName, String runId, Object input) {
        return null;
    }
}
