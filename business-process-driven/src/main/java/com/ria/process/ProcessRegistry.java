package com.ria.process;

import com.ria.process.flow.GatherUserInfoFlow;
import com.ria.process.flow.RegisterUserFlow;
import com.ria.process.flow.VerifyPhoneNumberFlow;
import com.ria.process.page.PageConfig;

import java.util.HashMap;
import java.util.Map;

public class ProcessRegistry {
    private final Map<String, Map<String, PageConfig>> registry = new HashMap<>();

    public ProcessRegistry() {
        // Register Processed here or load when the request is received
        register(RegisterUserFlow.BETO_HOME_PAGE);
        register(GatherUserInfoFlow.GATHER_USER_INFO_PAGE);
        register(VerifyPhoneNumberFlow.SEND_OTP_PAGE);
        register(VerifyPhoneNumberFlow.VERIFY_OTP_PAGE);
        register(VerifyPhoneNumberFlow.VERIFY_OTP_SUCCESS_PAGE);
        register(VerifyPhoneNumberFlow.VERIFY_OTP_FAILED_PAGE);
    }

    private void register(PageConfig config) {
        String namespace = config.getNamespace();
        String name = config.getName();
        if (registry.get(namespace) == null) {
            registry.put(namespace, new HashMap<>());
        }
        registry.get(namespace).put(name, config);
    }

    public PageConfig lookupPage(String namespace, String name) {
        if (registry.get(namespace) != null) {
            return registry.get(namespace).get(name);
        }
        // Handle this as a 404
        return null;
    }
}
