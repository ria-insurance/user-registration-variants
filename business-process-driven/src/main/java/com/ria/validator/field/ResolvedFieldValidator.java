package com.ria.validator.field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Base class for all Validators that are based on values extracted from given data objects.
 * @param <T> Data object from which field data is extracted.
 *
 * @author abhideep@ (Abhideep Singh)
 */
public class ResolvedFieldValidator<T> {
    private final Map<String, Field> fieldMap = new HashMap<>();
    private final Map<String, FieldValue<?>> fieldValueMap = new HashMap<>();

    private FieldValueResolver<T> resolver;

    protected ResolvedFieldValidator(Field... fields) {
        if (fields != null) {
            for (Field field : fields) {
                fieldMap.put(field.getFieldName(), field);
                fieldValueMap.put(field.getFieldName(), null);
            }
        }
    }

    public void init(FieldValueResolver<T> resolver) {
        this.resolver = resolver;
    }

    protected <V> V resolve(T data, Field field) {
        V value = resolver.resolve(data, field);
        fieldValueMap.put(field.getFieldName(), new FieldValue<V>(field.getFieldName(), value));
        return value;
    }

    public List<Field> getFields() {
        return new ArrayList<>(fieldMap.values());
    }

    public List<FieldValue<?>> getFieldValues() {
        return new ArrayList<>(fieldValueMap.values());
    }
}
