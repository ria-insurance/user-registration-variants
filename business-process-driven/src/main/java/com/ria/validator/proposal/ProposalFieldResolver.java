package com.ria.validator.proposal;

import com.ria.validator.field.Field;
import com.ria.validator.field.FieldValueGetter;
import com.ria.validator.field.FieldValueResolver;
import com.ria.validator.proposal.model.Proposal;

/**
 * Class that resolves the value of a Field given the field name
 *
 * @author abhideep@ (Abhideep Singh)
 */
public class ProposalFieldResolver implements FieldValueResolver<Proposal> {

    @Override
    public <V> V resolve(Proposal data, Field field) {
        FieldValueGetter<Proposal, ?> accessor = ((ProposalField) field).getFieldValueGetter();
        return (V) accessor.getValue(field, data);
    }
}
