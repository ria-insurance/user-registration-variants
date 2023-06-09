package com.ria.validator.field;

/**
 * Interface for all class that extract the data for a field from the given data object.
 *
 * @author abhideep@ (Abhideep Singh)
 */
public interface FieldValueGetter<T, V> {

    /**
     * Extract the field value.
     *
     * @param field Field for which the data needs to be extracted.
     * @param data Data object to extract the value from.
     * @return The extracted value
     */
    V getValue(Field field, T data);
}
