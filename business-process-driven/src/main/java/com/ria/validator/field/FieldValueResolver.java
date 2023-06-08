package com.ria.validator.field;

/**
 * Interface for classes that resolve a named field to a Value
 *
 * @param <T> Data that is source of the field data
 *
 * @author abhideep@ (Abhideep Singh)
 */
public interface FieldValueResolver<T> {

    <V> V resolve(T data, Field value);
}
