package com.ria.process.task;

public class PageTaskID {
    private final String namespace;
    private final String taskName;

    public PageTaskID(String namespace, String taskName) {
        this.namespace = namespace;
        this.taskName = taskName;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getTaskName() {
        return taskName;
    }
}
