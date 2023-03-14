package com.ria.process.page;

import com.ria.process.parameter.ParameterDefinition;
import com.ria.process.task.ConfigurablePageTask;
import com.ria.process.task.PageTask;
import com.ria.process.task.PageTaskID;
import com.ria.process.task.TriggerType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PageConfigBuilder {
    private String namespace;
    private String name;
    private PageRenderer renderer;
    private Map<String, ParameterDefinition> parameters;
    // private List<PageDataLoader> pageDataLoaders;
    private List<PageTask> tasks;


    public PageConfigBuilder(String namespace, String name) {
        this.namespace = namespace;
        this.name = name;
    }

    public PageConfigBuilder withRenderer(PageRenderer renderer) {
        this.renderer = renderer;
        return this;
    }

    public PageConfigBuilder withParameter(String name, ParameterDefinition parameter) {
        if (this.parameters == null) {
            this.parameters = new HashMap<>();
        }
        this.parameters.put(name, parameter);
        return this;
    }

    public PageConfigBuilder withTask(String namespace, String name, TriggerType triggerType,
                                      String engine, Map<String, String> dataMapping) {
        return withTask(new ConfigurablePageTask(new PageTaskID(namespace, name), triggerType, engine));
    }

    public PageConfigBuilder withTask(PageTask task) {
        if (this.tasks == null) {
            this.tasks = new ArrayList<>();
        }
        this.tasks.add(task);
        return this;
    }

    public PageConfig build() {
        return new PageConfig(namespace, name, parameters == null ? null : List.copyOf(parameters.values()),
                renderer, tasks);
    }

}
