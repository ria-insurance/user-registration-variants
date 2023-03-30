package com.ria.process.api.controllers;

import com.ria.process.api.config.*;
import com.ria.process.api.engine.FlowExecutor;
import com.ria.process.api.engine.WorkflowEngine;
import com.ria.process.api.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/proposal")
@Slf4j
public class ProposalAPI {


    @Autowired
    private FlowExecutor flowExecutor;

    private static final ApiMethodRegistry REGISTRY = new ApiMethodRegistry();

    static {

        REGISTRY.register(new ApiMethodConfig("/proposal/start", ApiMethodType.POST,
                "proposal", "", WorkflowMethodType.START,
                (DataConverter<StartProposalRequest, Map<String, Object>>) startProposalRequest -> new HashMap<>(),
                (DataConverter<String, Response>) input -> Response.success(), WorkflowEngine.camunda));
        REGISTRY.register(new ApiMethodConfig("/proposal/userDetails", ApiMethodType.POST,
                "proposal", "enterUserDetails", WorkflowMethodType.INPUT,
                (DataConverter<UserDetailsRequest, Map<String, Object>>) userDetailsRequest -> Map.of("name", userDetailsRequest.name(),
                        "email", userDetailsRequest.email(),
                        "city", userDetailsRequest.city()
                ),
                (DataConverter<String, Response>) input -> Response.success(), WorkflowEngine.camunda));
        REGISTRY.register(new ApiMethodConfig("/proposal/healthDetails", ApiMethodType.POST,
                "proposal", "enterHealthDetails", WorkflowMethodType.QUERY,
                (DataConverter<HealthDetailsRequest, Map<String, Object>>) healthDetailsRequest -> Map.of("age", healthDetailsRequest.age(),
                        "height", healthDetailsRequest.height(),
                        "weight", healthDetailsRequest.weight(),
                        "action", healthDetailsRequest.action()
                ),
                (DataConverter<Map<String, Object>, ProposalResponse>) input -> ProposalResponse.success((String) input.get("pageName")), WorkflowEngine.camunda));
    }

    @PostMapping("/start")
    public Response startProposal(@RequestBody StartProposalRequest request) {
        ApiMethodConfig config = REGISTRY.getConfig("/proposal/start", ApiMethodType.POST);
        return (Response) flowExecutor.execute(request.userId(), config, request);
    }

    @PostMapping("/userDetails")
    public Response userDetails(@RequestBody UserDetailsRequest request) {
        ApiMethodConfig config = REGISTRY.getConfig("/proposal/userDetails", ApiMethodType.POST);
        return (Response) flowExecutor.execute(request.userId(), config, request);
    }

    @PostMapping("/healthDetails")
    public ProposalResponse healthDetails(@RequestBody HealthDetailsRequest request) {
        ApiMethodConfig config = REGISTRY.getConfig("/proposal/healthDetails", ApiMethodType.POST);
        return (ProposalResponse) flowExecutor.execute(request.userId(), config, request);
    }

}
