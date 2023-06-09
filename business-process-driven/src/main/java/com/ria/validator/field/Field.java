package com.ria.validator.field;

/**
 * Interface for Enums that define the Field in a Data object
 *
 * @author abhideep@ (Abhideep Singh)
 */
public interface Field {

    /**
     * @return String name of the field.
     */
    String getFieldName();

    /**
     * @return Path used to extract the field value.
     */
    String getFieldPath();
}
