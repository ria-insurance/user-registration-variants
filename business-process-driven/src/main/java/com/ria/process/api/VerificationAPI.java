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

import java.util.HashMap;
import java.util.Map;

public class VerificationAPI {
    private static final ApiMethodRegistry REGISTRY = new ApiMethodRegistry();

    static {
        REGISTRY.register(new ApiMethodConfig("/verify/email/", ApiMethodType.POST,
                "SendVerificationEmail", "", WorkflowMethodType.START,
                (DataConverter<StartVerificationRequest, String>) StartVerificationRequest::getEmailAddress,
                (DataConverter<String, StartVerificationResponse>) input -> StartVerificationResponse.success()));
        REGISTRY.register(new ApiMethodConfig("/verify/email/otp", ApiMethodType.POST,
                "SendVerificationEmail", "emailVerified", WorkflowMethodType.INPUT,
                (DataConverter<VerifyOtpRequest, String>) VerifyOtpRequest::getOtp,
                (DataConverter<String, VerifyOtpResponse>) input -> VerifyOtpResponse.success()));
    }

    private final Map<String, String> runIdMap = new HashMap<>();

    public StartVerificationResponse startVerification(StartVerificationRequest request) {
        ApiMethodConfig config = REGISTRY.getConfig("/verify/email/", ApiMethodType.POST);
        String runId = startWorkflow(config.getWorkflowClass(), config.getRequestConverter().convert(request));
        // If it is a START then save the Run Id to be accessed later
        String key = request.getUserId() + " | " +  request.getEmailAddress();
        runIdMap.put(key, runId);
        return StartVerificationResponse.success();
    }

    public VerifyOtpResponse verifyOtp(VerifyOtpRequest request) {
        ApiMethodConfig config = REGISTRY.getConfig("/verify/email/", ApiMethodType.POST);
        // If it is not a START then lookup the Run Id to tell the workflow which Run to signal
        String key = request.getUserId() + " | " +  request.getEmailAddress();
        String runId = runIdMap.get(key);
        signalWorkflow(config.getWorkflowClass(), config.getMethodName(), runId,
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

    private void signalWorkflow(String workflowName, String methodName, String runId, Object input) {
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);
        WorkflowOptions options = WorkflowOptions.newBuilder().build();
        WorkflowStub workflow = client.newUntypedWorkflowStub(workflowName, options);
        workflow.signal(methodName, input);
        // TODO(abhideep): Run a query here to generate the response
    }
}
