package com.ria.process.page;

import com.ria.process.ExecutionContext;
import com.ria.process.parameter.ParameterDefinition;
import com.ria.process.task.ClassifiedTaskList;

import java.util.List;

public class ConfigurablePage implements Page {

    private final PageConfig config;
    private ClassifiedTaskList taskList;

    public ConfigurablePage(PageConfig config) {
        this.config = config;
    }

    @Override
    public String getName() {
        return config.getName();
    }

    @Override
    public void initialize(ExecutionContext context) {
        // Extract the parameters
        List<ParameterDefinition> parameters = config.getParameters();
        if (parameters != null) {
            for (ParameterDefinition parameter : parameters) {
                String name = parameter.getName();
                // TODO(abhideep): Use SouceType to use default Value Extractors
                Object value = parameter.getValueExtractor().extractValue();
                context.addParameterValue(name, value);
            }
        }
    }

    @Override
    public void load(ExecutionContext context) {
        // Call Data Loaders
    }

    @Override
    public void onLoad(ExecutionContext context) {
        // Send to Renderer
    }

    @Override
    public void execute(ExecutionContext context) {

    }

    @Override
    public PageRenderer render(ExecutionContext context) {
        return null;
    }
}
