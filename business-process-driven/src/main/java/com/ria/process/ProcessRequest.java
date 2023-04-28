package com.ria.process;

import java.util.Map;

public class ProcessRequest {
    private final String namespace; // base url like beto.ria.com
    private final String name; // uri after the base url e.g. /register/user
    private final String action; // TODO(abhideep): Convert to Enum
    private final Map<String, String> values; // Values received with the request
    private final String userId; // To be replaced with session data

    public ProcessRequest(String namespace, String name, String action, Map<String, String> values, String userId) {
        this.namespace = namespace;
        this.name = name;
        this.action = action;
        this.values = values;
        this.userId = userId;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getName() {
        return name;
    }

    public String getAction() {
        return action;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public String getUserId() {
        return userId;
    }
}
