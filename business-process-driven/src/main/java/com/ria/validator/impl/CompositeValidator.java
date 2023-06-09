package com.ria.validator.impl;

import com.ria.validator.field.FieldValueResolver;
import com.ria.validator.field.ResolvedFieldValidator;

import java.util.ArrayList;
import java.util.List;

/**
 * A validator based on a composition of multiple validators.
 *
 * @param <T> Data object that needs to be validated.
 *
 * @author abhideep@ (Abhideep Singh)
 */
public class CompositeValidator<T> extends ResolvedFieldValidator<T> {
    private final Operator operator;
    private final List<ResolvedFieldValidator<T>> validatorList = new ArrayList<>();

    public CompositeValidator() {
        this(Operator.AND);
    }

    public CompositeValidator(Operator operator) {
        this.operator = operator;
    }

    @Override
    public void init(FieldValueResolver<T> resolver) {
        super.init(resolver);
        for (ResolvedFieldValidator<T> validator : validatorList) {
            validator.init(resolver);
        }
    }

    @Override
    public boolean validate(T data) {
        boolean isValid = false;
        for (ResolvedFieldValidator<T> validator : validatorList) {
            if (!validator.validate(data)) {
                if (operator.equals(Operator.AND)) {
                    return false;
                }
            } else {
                if (operator.equals(Operator.OR)) {
                    return true;
                }
            }
        }
        return operator.equals(Operator.AND);
    }

    private enum Operator {
        OR,
        AND;
    }
}
