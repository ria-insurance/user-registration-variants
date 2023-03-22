package com.ria.process.api;

import com.ria.process.api.config.*;
import com.ria.process.api.model.StartVerificationRequest;
import com.ria.process.api.model.StartVerificationResponse;
import com.ria.process.api.model.VerifyOtpRequest;
import com.ria.process.api.model.VerifyOtpResponse;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import io.temporal.serviceclient.WorkflowServiceStubs;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class VerificationAPI {

    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private TaskService taskService;

    private static final ApiMethodRegistry REGISTRY = new ApiMethodRegistry();

    static {
        REGISTRY.register(new ApiMethodConfig("/verify/email/", ApiMethodType.POST,
                "SendVerificationEmail", "", WorkflowMethodType.START,
                (DataConverter<StartVerificationRequest, String>) StartVerificationRequest::emailAddress,
                (DataConverter<String, StartVerificationResponse>) input -> StartVerificationResponse.success()));
        REGISTRY.register(new ApiMethodConfig("/verify/email/otp", ApiMethodType.POST,
                "SendVerificationEmail", "emailVerified", WorkflowMethodType.INPUT,
                (DataConverter<VerifyOtpRequest, String>) VerifyOtpRequest::otp,
                (DataConverter<String, VerifyOtpResponse>) input -> VerifyOtpResponse.success()));

        REGISTRY.register(new ApiMethodConfig("/verify/email/v2", ApiMethodType.POST,
                "emailVerification", "", WorkflowMethodType.START,
                (DataConverter<StartVerificationRequest, Map<String,Object>>) startVerificationRequest -> Map.of("email", startVerificationRequest.emailAddress()),
                (DataConverter<String, StartVerificationResponse>) input -> StartVerificationResponse.success()));
        REGISTRY.register(new ApiMethodConfig("/verify/email/otp/v2", ApiMethodType.POST,
                "emailVerification", "userEntersOTP", WorkflowMethodType.INPUT,
                (DataConverter<VerifyOtpRequest, Map<String,Object>>) verifyOtpRequest -> Map.of("receivedOtp", verifyOtpRequest.otp()),
                (DataConverter<String, VerifyOtpResponse>) input -> VerifyOtpResponse.success()));

    }

    private final Map<String, String> runIdMap = new HashMap<>();

    public StartVerificationResponse startVerification(StartVerificationRequest request) {
        ApiMethodConfig config = REGISTRY.getConfig("/verify/email/", ApiMethodType.POST);
        String runId = startWorkflow(config.getWorkflowIdentifier(), config.getRequestConverter().convert(request));
        // If it is a START then save the Run Id to be accessed later
        String key = request.userId() + " | " +  request.emailAddress();
        runIdMap.put(key, runId);
        return StartVerificationResponse.success();
    }

    @PostMapping("/verify/email/v2")
    public StartVerificationResponse startVerificationV2(@RequestBody StartVerificationRequest request) {
        ApiMethodConfig config = REGISTRY.getConfig("/verify/email/v2", ApiMethodType.POST);
        String runId = startWorkflowCamunda(config.getWorkflowIdentifier(), config.getRequestConverter().convert(request));
        // If it is a START then save the Run Id to be accessed later
        String key = request.userId() + " | " +  request.emailAddress();
        runIdMap.put(key, runId);
        return StartVerificationResponse.success();
    }



    public VerifyOtpResponse verifyOtp(VerifyOtpRequest request) {
        ApiMethodConfig config = REGISTRY.getConfig("/verify/email/otp", ApiMethodType.POST);
        // If it is not a START then lookup the Run Id to tell the workflow which Run to signal
        String key = request.userId() + " | " +  request.emailAddress();
        String runId = runIdMap.get(key);
        signalWorkflow(config.getWorkflowIdentifier(), config.getMethodName(), runId,
                config.getRequestConverter().convert(request));
        return (VerifyOtpResponse) config.getResponseGenerator().convert(null);
    }


    @PostMapping("/verify/email/otp/v2")
    public VerifyOtpResponse verifyOtpV2(@RequestBody VerifyOtpRequest request) {
        ApiMethodConfig config = REGISTRY.getConfig("/verify/email/otp/v2", ApiMethodType.POST);
        // If it is not a START then lookup the Run Id to tell the workflow which Run to signal
        String key = request.userId() + " | " +  request.emailAddress();
        String runId = runIdMap.get(key);
        signalWorkflowCamunda(config.getWorkflowIdentifier(), config.getMethodName(), runId,
                config.getRequestConverter().convert(request));
        return (VerifyOtpResponse) config.getResponseGenerator().convert(null);
    }

    private String startWorkflow(String workflowName, Object input) {
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkflowOptions options = WorkflowOptions.newBuilder().build();
        WorkflowStub workflow = client.newUntypedWorkflowStub(workflowName, options);
        WorkflowExecution execution = workflow.start(input);
        return execution.getRunId();
    }

    private String startWorkflowCamunda(String workflowIdentifier, Object context) {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(workflowIdentifier,(Map<String, Object>) context);
        return processInstance.getProcessInstanceId();
    }

    private void signalWorkflowCamunda(String workflowName, String methodName, String runId, Object input) {
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


        taskService.complete(taskId, (Map<String, Object>)input);
    }

    private void signalWorkflow(String workflowName, String methodName, String runId, Object input) {
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkflowOptions options = WorkflowOptions.newBuilder().build();
        WorkflowStub workflow = client.newUntypedWorkflowStub(workflowName, options);
        workflow.signal(methodName, input);
        // TODO(abhideep): Run a query here to generate the response
    }
}
