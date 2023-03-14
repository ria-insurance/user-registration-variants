package com.ria.process.task;

import com.ria.process.ExecutionContext;

public interface TaskExecutor {

    String execute(ExecutionContext context);
}
