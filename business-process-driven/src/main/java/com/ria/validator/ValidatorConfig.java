package com.ria.validator;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Configuration of the List of Validators that need to run on a data object.
 *
 * @param <T> Type of Data object that needs to be validated.
 *
 * @author abhideep@ (Abhideep Singh)
 */
public class ValidatorConfig<T> {
    private final String namespace;
    private final String stage;
    private final List<String> validatorLabelList = new ArrayList<>();
    private final Map<String, Validator<T>> validatorMap = new HashMap<>();
    private final Map<String, ValidationError> validationErrorMap = new HashMap<>();

    public ValidatorConfig(String namespace, String stage) {
        this.namespace = namespace;
        this.stage = stage;
    }

    public ValidatorConfig<T> withValidator(String label, Validator<T> validator, ValidationError error) {
        //TODO(abhideep): Check Uniqueness
        validatorLabelList.add(label);
        validatorMap.put(label, validator);
        validationErrorMap.put(label, error);
        return this;
    }

    public String getNamespace() {
        return namespace;
    }

    public String getStage() {
        return stage;
    }

    public List<String> getValidatorLabels() {
        return validatorLabelList;
    }

    public Validator<T> getValidator(String label) {
        return validatorMap.get(label);
    }

    public ValidationError getValidationError(String label) {
        return validationErrorMap.get(label);
    }

}
