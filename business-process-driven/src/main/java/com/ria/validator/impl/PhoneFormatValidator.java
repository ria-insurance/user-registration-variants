package com.ria.validator.impl;

import com.ria.validator.Validator;
import com.ria.validator.field.Field;
import com.ria.validator.field.ResolvedFieldValidator;

/**
 * Validates the format of the phone number.
 *
 * @param <T> Data object that contains the phone number.
 * @author abhideep@ (Abhideep Singh)
 */
public class PhoneFormatValidator<T> extends ResolvedFieldValidator<T>
        implements Validator<T> {
    private final Field phoneField;
    private final int numberLength;

    public PhoneFormatValidator(Field phoneField, int numberLength) {
        this.phoneField = phoneField;
        this.numberLength = numberLength;
    }

    @Override
    public boolean validate(T data) {
        String phoneNumber = resolve(data, phoneField);
        return phoneNumber != null && phoneNumber.length() == numberLength;
    }
}
