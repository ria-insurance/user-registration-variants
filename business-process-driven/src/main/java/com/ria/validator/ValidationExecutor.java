package com.ria.validator;

import com.ria.validator.field.FieldValue;
import com.ria.validator.field.FieldValueResolver;
import com.ria.validator.field.ResolvedFieldValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Executes the Validators that are defined in a Validation Config
 *
 * @param <T> Type of data objetcs that the validators run on
 *
 * @author abhideep@ (Abhideep Singh)
 */
public class ValidationExecutor<T> {

    private final FieldValueResolver<T> resolver;

    public ValidationExecutor(FieldValueResolver<T> resolver) {
        this.resolver = resolver;
    }

    void validate(ValidatorConfig<T> config, T data) {
        List<String> validatorLabelList = config.getValidatorLabels();
        for (String label : validatorLabelList) {
            Validator<T> validator = config.getValidator(label);
            initValidator(validator);
            if (!validator.validate(data)) {
                ValidationError error = config.getValidationError(label);
                String errorMessage = error.getErrorMessage();
                int errorCode = error.getErrorCode();
                List<FieldValue<?>> values = getFieldValues(validator);
                // TODO(abhideep): Pass Error Code, Error Message, and Named Field Values in the Validation Response
            }
        }
    }

    private List<FieldValue<?>> getFieldValues(Validator<T> validator) {
        if (validator instanceof ResolvedFieldValidator) {
            ResolvedFieldValidator<T> resolvedFieldValidator = (ResolvedFieldValidator<T>) validator;
            return resolvedFieldValidator.getFieldValues();
        }
        return new ArrayList<>();
    }

    private void initValidator(Validator<T> validator) {
        if (validator instanceof ResolvedFieldValidator) {
            ResolvedFieldValidator<T> resolvedFieldValidator = (ResolvedFieldValidator<T>) validator;
            resolvedFieldValidator.init(resolver);
        }
    }


}
