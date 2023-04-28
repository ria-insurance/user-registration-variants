package com.ria.process.engine;

import com.ria.experiments.businessprocessdriven.registration.activities.UserDetailsRecording;
import com.ria.experiments.businessprocessdriven.registration.dtos.UserContactDetails;
import com.ria.process.ProcessRequest;
import com.ria.process.task.DataMapper;
import com.ria.process.task.PageTaskID;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;

import java.util.HashMap;
import java.util.Map;

public class TemporalWorkflowRegistry {

    private final Map<String, Map<String, WorkflowConfig<?>>> registry = new HashMap<>();

    public TemporalWorkflowRegistry() {
        register("ria.com", "record-user_details",
                new WorkflowConfig<UserContactDetails>("", "", new ExecutionFactory<>() {
                    @Override
                    public WorkflowExecution createExecution(WorkflowClient client, WorkflowOptions options,
                                                             UserContactDetails data) {
                        // TODO(abhideep): Use Reflection here to support reading data from DB
                        var workflow = client.newWorkflowStub(UserDetailsRecording.class, options);
                        // Asynchronous execution. This process will exit after making this call.
                        return WorkflowClient.start(workflow::recordUserDetails, data);
                    }
                }, new DataMapper<>() {
                    @Override
                    public UserContactDetails extract(ProcessRequest request) {
                        // TODO(abhideep): Actual implementation reads config from DB
                        return new UserContactDetails("firstname", "lastname", "1234567890");
                    }
                }));
    }

    @SuppressWarnings("rawtypes")
    WorkflowConfig getConfig(PageTaskID taskID) {
        if (registry.get(taskID.getNamespace()) == null) {
            return null;
        }
        return registry.get(taskID.getNamespace()).get(taskID.getTaskName());
    }

    private void register(String namespace, String name, WorkflowConfig<?> config) {
        registry.computeIfAbsent(namespace, k -> new HashMap<>());
        registry.get(namespace).put(name, config);
    }

    public abstract static class ExecutionFactory<T> {
        public abstract WorkflowExecution createExecution(WorkflowClient client, WorkflowOptions options, T data);
    }

    public record WorkflowConfig<T>(String queueName, String workflowId, ExecutionFactory<T> executionFactory,
                                    DataMapper<T> dataMapper) {
    }
}
