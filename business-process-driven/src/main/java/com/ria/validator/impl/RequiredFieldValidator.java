package com.ria.validator.impl;

import com.ria.validator.Validator;
import com.ria.validator.field.Field;
import com.ria.validator.field.ResolvedFieldValidator;

/**
 * Validator that makes sure that the given requeired field has a value.
 *
 * @param <T> Data object that contains the data for the field.
 *
 * @author abhideep@ (Abhideep Singh)
 */
public class RequiredFieldValidator<T> extends ResolvedFieldValidator<T>
        implements Validator<T> {
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
