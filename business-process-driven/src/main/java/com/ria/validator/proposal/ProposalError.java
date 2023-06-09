package com.ria.validator.proposal;

import com.ria.validator.ErrorCode;
import com.ria.validator.impl.ErrorCodeRegistry;

/**
 * Enums that define errors during Proposal creation.
 *
 * @author abhideep@ (Abhideep Singh)
 */
public enum ProposalError implements ErrorCode {
    EMAIL_REQUIRED(1001),
    INVALID_EMAIL(1002),
    PHONE_REQUIRED(1011),
    PHONE_INVALID(1012),
    ;

    private final int code;

    ProposalError(int code) {
        this.code = ErrorCodeRegistry.PROPOSAL_BASE_CODE + code;
    }

    @Override
    public int getCode() {
        return code;
    }
}
