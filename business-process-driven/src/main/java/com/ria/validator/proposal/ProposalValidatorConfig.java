package com.ria.validator.proposal;

import com.ria.validator.ValidationError;
import com.ria.validator.ValidatorConfig;
import com.ria.validator.impl.PhoneFormatValidator;
import com.ria.validator.impl.RequiredFieldValidator;
import com.ria.validator.proposal.model.Proposal;

/**
 * Validator configuration for the Proposal Generation Journey
 *
 * @author abhideep@ (Abhideep Singh)
 */
public class ProposalValidatorConfig {

    private static final ValidatorConfig<Proposal> CONFIG =
            new ValidatorConfig<Proposal>("beto|proposal", "gather-basic-info")
                    .withValidator("EMAIL_REQUIRED",
                            new RequiredFieldValidator<>(ProposalField.NOMINEE_EMAIL),
                            new ValidationError(ProposalError.EMAIL_REQUIRED,
                                    "Email Address is required"))
                    .withValidator("PHONE_INVALID",
                            new PhoneFormatValidator<>(ProposalField.NOMINEE_PHONE, 10),
                            new ValidationError(ProposalError.PHONE_INVALID,
                                    "Phone Number is invalid : %s", ProposalField.NOMINEE_PHONE))
                    .withValidator("PHONE_REQUIRED",
                                           new RequiredFieldValidator<>(ProposalField.NOMINEE_PHONE),
                            new ValidationError(ProposalError.PHONE_REQUIRED,
                                    " Phone number is required"));
}
