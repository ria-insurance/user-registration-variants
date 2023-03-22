package com.ria.process.api.config;

public class ApiMethodConfig {
    private final String apiName;
    private final ApiMethodType apiMethodType;

    private final String workflowIdentifier;
    private final String methodName;
    private final WorkflowMethodType methodType;

    private final DataConverter requestConverter;
    private final DataConverter responseGenerator;

    public ApiMethodConfig(String apiName, ApiMethodType apiMethodType,
                           String workflowIdentifier, String methodName, WorkflowMethodType methodType,
                           DataConverter requestConverter, DataConverter responseGenerator) {
        this.apiName = apiName;
        this.apiMethodType = apiMethodType;
        this.workflowIdentifier = workflowIdentifier;
        this.methodName = methodName;
        this.methodType = methodType;
        this.requestConverter = requestConverter;
        this.responseGenerator = responseGenerator;
    }

    public String getApiName() {
        return apiName;
    }

    public ApiMethodType getApiMethodType() {
        return apiMethodType;
    }

    public String getWorkflowIdentifier() {
        return workflowIdentifier;
    }

    public String getMethodName() {
        return methodName;
    }

    public WorkflowMethodType getMethodType() {
        return methodType;
    }

    public DataConverter getRequestConverter() {
        return requestConverter;
    }

    public DataConverter getResponseGenerator() {
        return responseGenerator;
    }
}
