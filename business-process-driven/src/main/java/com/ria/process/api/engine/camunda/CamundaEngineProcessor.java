package com.ria.process.api.engine.camunda;

import com.ria.process.api.config.UserFlowMap;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.HistoryService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.runtime.VariableInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CamundaEngineProcessor implements com.ria.process.api.engine.EngineProcessor {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private UserFlowMap userFlowMap;


    @Override
    public String startWorkflow(String userId, String workflowIdentifier, Object context) {
        Map<String, Object> variables = (Map<String, Object>) context;
        //variables.put("businessKey", UUID.randomUUID().toString());
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(workflowIdentifier, variables);
        String processInstanceId = processInstance.getProcessInstanceId();
        log.info("Camunda process created {}", processInstanceId);
        userFlowMap.updateForAnyChildProcess(userId, processInstanceId);
        return processInstanceId;
    }

    @Override
    public void signalWorkflow(String userId, String workflowName, String methodName, String runId, Object input) {
        findTaskAndComplete(runId, methodName, (Map<String, Object>) input);
        log.info("Process variables are : {}", processVariables(runId));
        userFlowMap.updateForAnyChildProcess(userId, runId);
    }

    @Override
    public Object executeAndQuery(String userId, String workflowName, String methodName, String runId, Object input) {
        findTaskAndComplete(runId, methodName, (Map<String, Object>) input);

        Map<String, Object> result = processVariables(runId);
        log.info("Process variables are : {}", result);
        userFlowMap.updateForAnyChildProcess(userId, runId);

        return result;
    }

    private Map<String, Object> processVariables(String runId) {
        Map<String, Object> list = runtimeService.createVariableInstanceQuery()
                .processInstanceIdIn(runId)
                .list().stream().collect(Collectors.toMap(VariableInstance::getName, VariableInstance::getValue));
        return list;
    }

    private void findTaskAndComplete(String runId, String methodName, Map<String, Object> input) {
        String taskId = taskService.createTaskQuery()
                .processInstanceId(runId)
                .list()
                .stream()
                .filter(task -> task.getTaskDefinitionKey().equals(methodName))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        String.format("Could not find task with name %s on processInstanceId %s",
                                "userEntersOTP",
                                runId))).getId();

        taskService.complete(taskId, input);
        log.info("Task {} completed on camunda process created {}", methodName, runId);
    }

    //ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(runId).singleResult();

}
