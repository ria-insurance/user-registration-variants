package com.ria.validator.impl;

import com.ria.validator.Validator;
import com.ria.validator.field.Field;
import com.ria.validator.field.ResolvedFieldValidator;

public class RequiredFieldValidator<T> extends ResolvedFieldValidator<T> implements Validator<T> {
    private final Field field;

    public RequiredFieldValidator(Field field) {
        super(field);
        this.field = field;
    }

    @Override
    public boolean validate(T data) {
        Object value = resolve(data, field);
        return (value != null);
    }
}
