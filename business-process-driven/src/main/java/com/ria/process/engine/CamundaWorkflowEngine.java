package com.ria.process.engine;

import com.ria.process.task.PageTaskID;
import com.ria.process.task.TaskExecutor;

public class CamundaWorkflowEngine {

    public static TaskExecutor getTaskExecutor(PageTaskID taskID) {
        // TODO: Add registry based implementation here
        return new TaskExecutor() {
            @Override
            public String execute(Object data) {
                return null;
            }
        };
    }
}
