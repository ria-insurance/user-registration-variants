package com.ria.validator;

/**
 * Interface for all Configurable Validators in the System
 *
 * @author abhideep@ (Abhideep Singh)
 */
public interface Validator<T> {
    /**
     * Validates if a certain condition is met
     *
     * @param data data which needs to be validated.
     * @return true if the validation passes, false otherwise
     */
    boolean validate(T data);
}
