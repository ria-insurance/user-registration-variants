package com.ria.process;

import java.util.HashMap;
import java.util.Map;

public class ExecutionContext {
    private final Map<String, Object> parameterValues = new HashMap<>();

    public void addParameterValue(String name, Object value) {
        parameterValues.put(name, value);
    }
}
