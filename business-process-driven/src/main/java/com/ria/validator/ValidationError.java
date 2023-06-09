package com.ria.validator;

import com.ria.validator.field.Field;

import java.util.List;

/**
 * Definition of an Error that is thrown when a Validation Fails.
 *
 * @author abhideep@ (Abhideep Singh)
 */
public class ValidationError {
    private final ErrorCode errorCode;
    private final String message;
    private final String format;
    private final List<Field> fieldList;

    public ValidationError(ErrorCode errorCode, String errorMessage, Field... fields) {
        this.errorCode = errorCode;
        if (fields != null) {
            this.fieldList = List.of(fields);
            this.format = errorMessage;
            this.message = null;
        } else {
            this.message = errorMessage;
            this.fieldList = null;
            this.format = null;
        }
    }

    public String getErrorMessage() {
        if (format != null) {
            return String.format(format, toArray(fieldList));
        } else {
            return message;
        }
    }

    private Object[] toArray(List<Field> fieldList) {
        // TODO(abhideep): Extract the Values from the field to include in the Error Message
        return fieldList.toArray();
    }

    public int getErrorCode() {
        return errorCode.getCode();
    }
}
