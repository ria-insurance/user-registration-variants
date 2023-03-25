package com.ria.process.api.controllers;

import com.ria.process.api.config.*;
import com.ria.process.api.engine.camunda.CamundaEngineProcessor;
import com.ria.process.api.engine.temporal.TemporalEngineProcessor;
import com.ria.process.api.model.StartVerificationRequest;
import com.ria.process.api.model.StartVerificationResponse;
import com.ria.process.api.model.VerifyOtpRequest;
import com.ria.process.api.model.VerifyOtpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class VerificationAPI {


    @Autowired
    private TemporalEngineProcessor temporalEngineProcessor;

    @Autowired
    private UserFlowMap userFlowMap;

    @Autowired
    private CamundaEngineProcessor camundaEngineProcessor;

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
                (DataConverter<StartVerificationRequest, Map<String, Object>>) startVerificationRequest -> Map.of("email", startVerificationRequest.emailAddress()),
                (DataConverter<String, StartVerificationResponse>) input -> StartVerificationResponse.success()));
        REGISTRY.register(new ApiMethodConfig("/verify/email/otp/v2", ApiMethodType.POST,
                "emailVerification", "userEntersOTP", WorkflowMethodType.INPUT,
                (DataConverter<VerifyOtpRequest, Map<String, Object>>) verifyOtpRequest -> Map.of("receivedOtp", verifyOtpRequest.otp()),
                (DataConverter<String, VerifyOtpResponse>) input -> VerifyOtpResponse.success()));

    }

    private final Map<String, String> runIdMap = new HashMap<>();

    public StartVerificationResponse startVerification(StartVerificationRequest request) {
        ApiMethodConfig config = REGISTRY.getConfig("/verify/email/", ApiMethodType.POST);
        String runId = temporalEngineProcessor.startWorkflow(config.getWorkflowIdentifier(), config.getRequestConverter().convert(request));
        // If it is a START then save the Run Id to be accessed later
        String key = request.userId() + " | " + request.emailAddress();
        runIdMap.put(key, runId);
        return StartVerificationResponse.success();
    }

    @PostMapping("/verify/email/v2")
    public StartVerificationResponse startVerificationV2(@RequestBody StartVerificationRequest request) {
        ApiMethodConfig config = REGISTRY.getConfig("/verify/email/v2", ApiMethodType.POST);
        String runId = camundaEngineProcessor.startWorkflow(request.userId(), config.getWorkflowIdentifier(), config.getRequestConverter().convert(request));
        // If it is a START then save the Run Id to be accessed later
       // String key = request.userId() + " | " + request.emailAddress();
        //runIdMap.put(key, runId);
        userFlowMap.add(request.userId(),config.getWorkflowIdentifier(), runId);
        return StartVerificationResponse.success();
    }


    public VerifyOtpResponse verifyOtp(VerifyOtpRequest request) {
        ApiMethodConfig config = REGISTRY.getConfig("/verify/email/otp", ApiMethodType.POST);
        // If it is not a START then lookup the Run Id to tell the workflow which Run to signal
        String key = request.userId() + " | " + request.emailAddress();
        String runId = runIdMap.get(key);
        temporalEngineProcessor.signalWorkflow(config.getWorkflowIdentifier(), config.getMethodName(), runId,
                config.getRequestConverter().convert(request));
        return (VerifyOtpResponse) config.getResponseGenerator().convert(null);
    }


    @PostMapping("/verify/email/otp/v2")
    public VerifyOtpResponse verifyOtpV2(@RequestBody VerifyOtpRequest request) {
        ApiMethodConfig config = REGISTRY.getConfig("/verify/email/otp/v2", ApiMethodType.POST);
        // If it is not a START then lookup the Run Id to tell the workflow which Run to signal
        //String key = request.userId() + " | " + request.emailAddress();
        //String runId = runIdMap.get(key);
        String runId = userFlowMap.get(request.userId(), config.getWorkflowIdentifier());
        log.info("Look up for key is runId {}", runId);
        camundaEngineProcessor.signalWorkflow(request.userId(), config.getWorkflowIdentifier(), config.getMethodName(), runId,
                config.getRequestConverter().convert(request));
        return (VerifyOtpResponse) config.getResponseGenerator().convert(null);
    }


}
