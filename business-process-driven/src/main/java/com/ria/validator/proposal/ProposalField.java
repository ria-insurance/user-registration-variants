package com.ria.validator.proposal;

import com.ria.validator.field.Field;
import com.ria.validator.field.FieldValueGetter;
import com.ria.validator.proposal.model.Proposal;

/**
 * Field definitions for a Proposal data object.
 *
 * @author abhideep@ (Abhideep Singh)
 */
public enum ProposalField implements Field {
    PROPOSAL_ID("proposalID",
            (FieldValueGetter<Proposal, Integer>) (field, data) -> data.getProposalID()),
    NOMINEE_EMAIL("nominee.email.address",
            (FieldValueGetter<Proposal, String>) (field, data) -> data.getNominee().getEmail()),
    NOMINEE_PHONE("nominee.phone.number",
            (FieldValueGetter<Proposal, String>) (field, data) -> data.getNominee().getPhone()),
    ;

    private final String path;
    private final FieldValueGetter<Proposal, ?> accessor;

    ProposalField(String path, FieldValueGetter<Proposal, ?> accessor) {
        this.path = path;
        this.accessor = accessor;
    }

    @Override
    public String getFieldPath() {
        return path;
    }

    @Override
    public String getFieldName() {
        return name();
    }

    public FieldValueGetter<Proposal, ?> getFieldValueGetter() {
        return accessor;
    }
}
