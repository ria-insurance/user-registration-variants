package com.ria.validator.field;

/**
 * Class that stores the value of a Field along with its name.
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

    /**
     * @return Name of the Field.
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * @return The field name and value pair.
     */
    public V getFieldValue() {
        return fieldValue;
    }
}
