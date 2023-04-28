package com.ria.process.task;



import com.ria.process.ExecutionContext;

import java.util.List;

public class ConfigurablePageTask implements PageTask {

    private final PageTaskID taskID;
    private final TriggerType triggerType;
    private final String engine;

    public ConfigurablePageTask(PageTaskID taskID, TriggerType triggerType, String engine) {
        this.taskID = taskID;
        this.triggerType = triggerType;
        this.engine = engine;
    }

    @Override
    public PageTaskID getId() {
        return taskID;
    }

    @Override
    public TriggerType getTriggerType() {
        return triggerType;
    }

    @Override
    public String getExecutionEngine() {
        return engine;
    }

    @Override
    public List<PageTaskID> getDependencies() {
        return null;
    }

    @Override
    public boolean execute(ExecutionContext context) {
        return false;
    }
}
