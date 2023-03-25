package com.ria.process.api.controllers;

import com.ria.process.api.config.*;
import com.ria.process.api.engine.camunda.CamundaEngineProcessor;
import com.ria.process.api.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/proposal/v2")
@Slf4j
public class ProposalComposedAPI {


    @Autowired
    private CamundaEngineProcessor camundaEngineProcessor;

    @Autowired
    private UserFlowMap userFlowMap;

    private static final ApiMethodRegistry REGISTRY = new ApiMethodRegistry();

    static {

        REGISTRY.register(new ApiMethodConfig("/proposal/start", ApiMethodType.POST,
                "proposalComposed", "", WorkflowMethodType.START,
                (DataConverter<StartProposalRequest, Map<String, Object>>) startProposalRequest -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("businessKey", UUID.randomUUID().toString());
                    return map;
                },
                (DataConverter<String, Response>) input -> Response.success()));
        REGISTRY.register(new ApiMethodConfig("/proposal/userDetails", ApiMethodType.POST,
                "proposalComposed", "enterUserDetails", WorkflowMethodType.INPUT,
                (DataConverter<UserDetailsRequest, Map<String, Object>>) userDetailsRequest -> Map.of("name", userDetailsRequest.name(),
                        "email", userDetailsRequest.email(),
                        "city", userDetailsRequest.city()
                ),
                (DataConverter<String, Response>) input -> Response.success()));
        REGISTRY.register(new ApiMethodConfig("/proposal/healthDetails", ApiMethodType.POST,
                "proposalComposed", "enterHealthDetails", WorkflowMethodType.INPUT,
                (DataConverter<HealthDetailsRequest, Map<String, Object>>) healthDetailsRequest -> Map.of("age", healthDetailsRequest.age(),
                        "height", healthDetailsRequest.height(),
                        "weight", healthDetailsRequest.weight(),
                        "action", healthDetailsRequest.action()
                ),
                (DataConverter<Map<String, Object>, ProposalResponse>) input -> ProposalResponse.success((String) input.get("pageName"))));



    }

    @PostMapping("/start")
    public Response startProposal(@RequestBody StartProposalRequest request) {
        ApiMethodConfig config = REGISTRY.getConfig("/proposal/start", ApiMethodType.POST);
        String runId = camundaEngineProcessor.startWorkflow(request.userId(), config.getWorkflowIdentifier(), config.getRequestConverter().convert(request));
        // If it is a START then save the Run Id to be accessed later
        //String key = request.userId() ;
        //runIdMap.put(key, runId);
        userFlowMap.add(request.userId(), config.getWorkflowIdentifier(), runId);
        return (Response) config.getResponseGenerator().convert(null);
    }

    @PostMapping("/userDetails")
    public Response userDetails(@RequestBody UserDetailsRequest request) {
        ApiMethodConfig config = REGISTRY.getConfig("/proposal/userDetails", ApiMethodType.POST);
        // If it is not a START then lookup the Run Id to tell the workflow which Run to signal
        String key = request.userId();
        //String runId = runIdMap.get(key);
        String runId =  userFlowMap.get(request.userId(), config.getWorkflowIdentifier());
        camundaEngineProcessor.signalWorkflow(request.userId(), config.getWorkflowIdentifier(), config.getMethodName(), runId,
                config.getRequestConverter().convert(request));
        return (Response) config.getResponseGenerator().convert(null);
    }

    @PostMapping("/healthDetails")
    public ProposalResponse healthDetails(@RequestBody HealthDetailsRequest request) {
        ApiMethodConfig config = REGISTRY.getConfig("/proposal/healthDetails", ApiMethodType.POST);
        // If it is not a START then lookup the Run Id to tell the workflow which Run to signal
        String key = request.userId();
        //String runId = runIdMap.get(key);
        String runId =  userFlowMap.get(request.userId(), config.getWorkflowIdentifier());
        Map<String, Object> result = camundaEngineProcessor.executeAndQuery(request.userId(), config.getWorkflowIdentifier(), config.getMethodName(), runId,
                config.getRequestConverter().convert(request));
        return (ProposalResponse) config.getResponseGenerator().convert(result);
    }

}
