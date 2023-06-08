package com.ria.validator.field;

/**
 * Class that store the value of a Field along with its name.
 *
 * @author abhideep@ (Abhideep Singh)
 */
public class FieldValue<V> {
    private final String fieldName;
    private final V fieldValue;

    public FieldValue(String fieldName, V fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public V getFieldValue() {
        return fieldValue;
    }
}
