package com.ria.process;

import com.ria.process.page.PageConfig;

import java.util.Map;

public class ConfigurableProcessFlow implements ProcessFlow {
    private String name;

    // TODO(abhideep): Add support for multiple pages for each Task
    private Map<String, PageConfig> routes;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getCurrentTaskName() {
        return null;
    }
}
