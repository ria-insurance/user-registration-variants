package com.ria.process;

import com.ria.process.page.PageConfig;

import java.util.Map;

public class ConfigurableProcessFlow {
    private String name;
    // TODO(abhideep): Add support for multiple pages for each Task
    private Map<String, PageConfig> routes;

}
