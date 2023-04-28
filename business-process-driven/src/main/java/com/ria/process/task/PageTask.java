package com.ria.process.task;



import com.ria.process.ExecutionContext;

import java.util.List;

public interface PageTask {
    PageTaskID getId();

    TriggerType getTriggerType();

    String getExecutionEngine();

    List<PageTaskID> getDependencies();

    // TODO(abhideep): Introduce Futures
    boolean execute(ExecutionContext context);
}
