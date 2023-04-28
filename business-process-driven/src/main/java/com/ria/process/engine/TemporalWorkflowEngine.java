package com.ria.process.engine;

import com.ria.process.task.DataMapper;
import com.ria.process.task.PageTaskID;
import com.ria.process.task.TaskExecutor;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.serviceclient.WorkflowServiceStubs;

public class TemporalWorkflowEngine {

    private static final TemporalWorkflowRegistry REGISTRY = new TemporalWorkflowRegistry();

    public static <T> TaskExecutor<T> getTaskExecutor(PageTaskID taskID) {
        TemporalWorkflowRegistry.WorkflowConfig<T> config = REGISTRY.getConfig(taskID);
        return (input) -> startWorkflow(config, input);
    }

    public static <T> DataMapper<T> getDataMapper(PageTaskID taskID) {
        TemporalWorkflowRegistry.WorkflowConfig<T> config = REGISTRY.getConfig(taskID);
        return config.dataMapper();
    }

    public static <T> String startWorkflow(TemporalWorkflowRegistry.WorkflowConfig<T> config, T data) {
        // WorkflowServiceStubs is a gRPC stubs wrapper that talks to the local Docker instance of the Temporal server.
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowOptions.Builder options = WorkflowOptions.newBuilder();
        if (config.queueName() != null) {
            options.setTaskQueue(config.queueName());
        }
        if (config.workflowId() != null) {
            options.setWorkflowId(config.workflowId());

        }
        // WorkflowClient can be used to start, signal, query, cancel, and terminate Workflows.
        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkflowExecution execution = config.executionFactory().createExecution(client, options.build(), data);
        return execution.getRunId();
    }
}
