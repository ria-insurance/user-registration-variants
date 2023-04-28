package com.ria.process.page;

import com.ria.process.parameter.ParameterDefinition;
import com.ria.process.task.PageTask;

import java.util.List;

public class PageConfig {
    private final PageID pageID;
    private final List<ParameterDefinition> parameters;
    private final List<PageTask> tasks;
    private final PageRenderer renderer;
    // private List<PageDataLoader> pageDataLoaders;

    public PageConfig(String namespace, String name, List<ParameterDefinition> parameters, PageRenderer renderer,
                      List<PageTask> tasks) {
        this.pageID = new PageID(namespace, name);
        this.parameters = parameters;
        this.renderer = renderer;
        this.tasks = tasks;
    }

    public String getNamespace() {
        return pageID.getNamespace();
    }

    public String getName() {
        return pageID.getPageName();
    }

    public PageRenderer getRenderer() {
        return renderer;
    }

    public List<ParameterDefinition> getParameters() {
        return parameters;
    }

    public List<PageTask> getTasks() {
        return tasks;
    }
}
