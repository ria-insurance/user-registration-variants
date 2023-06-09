package com.ria.validator.proposal.model;

/**
 * Data object that has the information for a Proposal.
 *
 * @author abhideep@ (Abhideep Singh)
 */
public class Proposal {
    private int proposalID;
    private Nominee nominee;

    public int getProposalID() {
        return proposalID;
    }

    public Nominee getNominee() {
        return nominee;
    }

    public class Nominee {
        private String email;
        private String phone;

        public String getEmail() {
            return email;
        }

        public String getPhone() {
            return phone;
        }
    }
}
