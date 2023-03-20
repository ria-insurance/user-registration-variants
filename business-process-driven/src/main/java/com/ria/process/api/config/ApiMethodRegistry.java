package com.ria.process.api.config;

import java.util.HashMap;
import java.util.Map;

public class ApiMethodRegistry {

    private final Map<String, ApiMethodConfig> configMap = new HashMap<>();

    public void register(ApiMethodConfig config) {
        configMap.put(getKey(config.getApiName(), config.getApiMethodType()) , config);
    }

    public ApiMethodConfig getConfig(String apiMethodName, ApiMethodType methodType) {
        return configMap.get(getKey(apiMethodName, methodType));
    }

    private String getKey(String apiMethodName, ApiMethodType methodType) {
        return apiMethodName + " | " + methodType.name();
    }
}
