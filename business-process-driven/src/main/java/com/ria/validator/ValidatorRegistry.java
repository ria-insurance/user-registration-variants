package com.ria.validator;

import java.util.HashMap;
import java.util.Map;

/**
 * A global registry of Validator Configuration that are accessible via Namespace or Namespace and Stage
 *
 * @author abhideep@ (Abhideep Singh)
 */
public class ValidatorRegistry {
    private final Map<String, Map<String, ValidatorConfig<?>>> registry = new HashMap<>();

    public void register(ValidatorConfig<?> config) {
        String namespace = config.getNamespace();
        String stage = config.getStage();
        if (!registry.containsKey(namespace)) {
            registry.put(namespace, new HashMap<>());
        }
        registry.get(namespace).put(stage, config);
    }

    public Map<String, ValidatorConfig<?>> getValidators(String namespace) {
        return registry.get(namespace);
    }

    public ValidatorConfig<?> getValidators(String namespace, String stage) {
        return registry.get(namespace).get(stage);
    }

}
