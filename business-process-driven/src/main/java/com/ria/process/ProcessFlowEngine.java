package com.ria.process;

import com.ria.process.engine.CamundaWorkflowEngine;
import com.ria.process.engine.TemporalWorkflowEngine;
import com.ria.process.page.ConfigurablePage;
import com.ria.process.page.PageConfig;
import com.ria.process.task.ClassifiedTaskList;
import com.ria.process.task.PageTask;
import com.ria.process.task.TriggerType;

import java.util.List;

public class ProcessFlowEngine {
    private static final ProcessRegistry REGISTRY = new ProcessRegistry();

    public Long processRequest(ProcessRequest request) {
        // Lookup Process based on request
        String namespace = request.getNamespace();
        String name = request.getName();
        PageConfig config = REGISTRY.lookupPage(namespace, name);
        if (config == null) {
            // Process 404 here
            return null;
        }
        ConfigurablePage page = new ConfigurablePage(config);
        ClassifiedTaskList taskList = new ClassifiedTaskList(config.getTasks());
        // TODO(abhideep): Copy request parameters into the context
        ExecutionContext context = null;
        page.initialize(context);
        // TODO(abhideep): Remove the if-else
        if (TriggerType.ON_LOAD.name().equals(request.getAction())) {
            page.onLoad(context);
            execute(taskList.getTaskList(TriggerType.ON_LOAD), request);
        } else if (TriggerType.ON_SUBMIT.name().equals(request.getAction())) {
            // TODO(abhideep): Add support for verification
            page.execute(context);
            execute(taskList.getTaskList(TriggerType.ON_SUBMIT), request);
        }
        // Start a process with the given name and return the Process Instance Id
        return null;
    }

    private void execute(List<PageTask> taskList, ProcessRequest request) {
        // Call the appropriate Workflow Engine here and store the Process Instance Id
        for (PageTask task : taskList) {
            if (task.getExecutionEngine().equals("Temporal")) {
                // TODO(abhideep): Save this instance Id somewhere
                String instanceId = TemporalWorkflowEngine.getTaskExecutor(task.getId())
                        .execute(TemporalWorkflowEngine.getDataMapper(task.getId()).extract(request));
            } else if (task.getExecutionEngine().equals("Camunda")) {
                // TODO(abhideep): Add Implementation here
            }
        }
    }
}
