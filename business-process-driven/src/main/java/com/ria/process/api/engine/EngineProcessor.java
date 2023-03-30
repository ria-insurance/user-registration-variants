package com.ria.process.api.engine;

import java.util.Map;

public interface EngineProcessor {
    String startWorkflow(String userId, String workflowIdentifier, Object context);

    void signalWorkflow(String userId, String workflowName, String methodName, String runId, Object input);

    Object executeAndQuery(String userId, String workflowName, String methodName, String runId, Object input);
}
