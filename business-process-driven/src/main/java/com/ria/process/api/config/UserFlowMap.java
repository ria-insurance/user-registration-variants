package com.ria.process.api.config;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class UserFlowMap {

    @Autowired
    private RuntimeService runtimeService;

    private final Map<String, String> runIdMap = new HashMap<>();


    public void add(String userId, String workflowIdentifier, String runId) {
        runIdMap.putIfAbsent(
                getKey(userId, workflowIdentifier),
                runId);
    }

    private static String getKey(String userId, String workflowIdentifier) {
        return userId.concat(" | ").concat(workflowIdentifier);
    }

    public String get(String userId, String workflowIdentifier) {
        return runIdMap.get(getKey(userId, workflowIdentifier));
    }

    public void updateForAnyChildProcess(String userId, String processInstanceId) {

        List<ProcessInstance> listOfChildProcess = runtimeService.createProcessInstanceQuery()
                .superProcessInstanceId(processInstanceId).list();
        log.info("Child process {} {} ", listOfChildProcess, listOfChildProcess.size());
        for (ProcessInstance ofChildProcess : listOfChildProcess) {

            String processIdentifier = ofChildProcess.getProcessDefinitionId().split(":")[0];
            log.info("Child process details {} {} {} {}",
                    ofChildProcess.getProcessInstanceId(),
                    ofChildProcess.getId(),
                    processIdentifier,
                    ofChildProcess.getBusinessKey()

            );
            add(userId, processIdentifier, ofChildProcess.getProcessInstanceId());
        }



    }
}
