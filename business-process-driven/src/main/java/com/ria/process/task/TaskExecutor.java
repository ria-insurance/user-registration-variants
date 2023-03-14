package com.ria.process.task;

import com.ria.process.ExecutionContext;

public interface TaskExecutor<T> {

    String execute(T data);
}
