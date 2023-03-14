package com.ria.process.engine;

import com.ria.process.ExecutionContext;
import com.ria.process.task.PageTaskID;
import com.ria.process.task.TaskExecutor;

public class CamundaWorkflowEngine {

    public static TaskExecutor getTaskExecutor(PageTaskID taskID) {
        return new TaskExecutor() {
            @Override
            public String execute(ExecutionContext context) {
                // TODO(abhideep):Add implementation here
                return "0";
            }
        };
    }
}
