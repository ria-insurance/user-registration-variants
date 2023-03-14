package com.ria.process.task;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassifiedTaskList {
    private Map<TriggerType, List<PageTask>> taskMap = new HashMap<>();

    public ClassifiedTaskList(List<PageTask> tasks) {
        if (tasks != null) {
            for (PageTask task : tasks) {
                taskMap.put(task.getTriggerType(), tasks);
            }
        }
    }

    public List<PageTask> getTaskList(TriggerType triggerType) {
        List<PageTask> list = taskMap.get(triggerType);
        return list == null ? Collections.emptyList() : list;
    }
}
