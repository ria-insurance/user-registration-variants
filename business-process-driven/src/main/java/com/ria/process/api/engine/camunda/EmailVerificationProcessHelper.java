package com.ria.process.api.engine.camunda;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.task.Task;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class EmailVerificationProcessHelper {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    //@EventListener
    private void processPostDeploy(PostDeployEvent event) {
        log.info("STARTING PROCESS------");
        Map context = Map.of("email", "raghav@ria.insure");
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("emailVerification", context);
        log.info("Process instance id is : {} ", processInstance.getProcessInstanceId());
    }

    public void enterOtp(String receivedOtp, String processInstanceId){




        String taskId = taskService.createTaskQuery()
                .processInstanceId(processInstanceId)
                .list()
                .stream()
                .filter(task -> task.getTaskDefinitionKey().equals("userEntersOTP"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        String.format("Could not find task with name %s on processInstanceId %s",
                                "userEntersOTP",
                                processInstanceId))).getId();

        Map<String, Object> variables = Map.of("receivedOtp",receivedOtp);
        taskService.complete(taskId, variables);
    }
}
