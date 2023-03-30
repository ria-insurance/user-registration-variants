package com.ria.process.api.engine;

import com.ria.process.api.config.ApiMethodConfig;
import com.ria.process.api.config.UserFlowMap;
import com.ria.process.api.config.WorkflowMethodType;
import com.ria.process.api.engine.camunda.CamundaEngineProcessor;
import com.ria.process.api.engine.temporal.TemporalEngineProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FlowExecutor {

    @Autowired
    private CamundaEngineProcessor camundaEngineProcessor;

    @Autowired
    private UserFlowMap userFlowMap;

    @Autowired
    TemporalEngineProcessor temporalEngineProcessor;

    public Object execute(String userId, ApiMethodConfig config, Object request) {
        Object responseInput = null;
        if (config.getMethodType().equals(WorkflowMethodType.START)) {
            String runId = getEngineProcessor(config).startWorkflow(userId, config.getWorkflowIdentifier(), config.getRequestConverter().convert(request));
            userFlowMap.add(userId, config.getWorkflowIdentifier(), runId);
        }
        else if (config.getMethodType().equals(WorkflowMethodType.INPUT)) {
            String runId = userFlowMap.get(userId, config.getWorkflowIdentifier());
            getEngineProcessor(config)
                    .signalWorkflow(userId, config.getWorkflowIdentifier(), config.getMethodName(), runId, config.getRequestConverter().convert(request));
        }
        else if (config.getMethodType().equals(WorkflowMethodType.QUERY)) {
            String runId = userFlowMap.get(userId, config.getWorkflowIdentifier());
            responseInput = getEngineProcessor(config)
                    .executeAndQuery(userId, config.getWorkflowIdentifier(), config.getMethodName(), runId, config.getRequestConverter().convert(request));
        }


        return config.getResponseGenerator().convert(responseInput);
    }

    public EngineProcessor getEngineProcessor(ApiMethodConfig config) {
        if (config.workflowEngine().equals(WorkflowEngine.temporal)) {
            return temporalEngineProcessor;
        } else
            return camundaEngineProcessor;
    }

}
