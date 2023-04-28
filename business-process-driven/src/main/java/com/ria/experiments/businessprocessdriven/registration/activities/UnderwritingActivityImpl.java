package com.ria.experiments.businessprocessdriven.registration.activities;

import com.ria.experiments.businessprocessdriven.registration.dtos.MedicalQuestions;
import com.ria.experiments.businessprocessdriven.registration.dtos.UnderwritingDecision;

public class UnderwritingActivityImpl implements UnderwritingActivity {
    @Override
    public UnderwritingDecision doUnderwriting(MedicalQuestions medicalQuestions) {
        if(medicalQuestions.age() > 60)
            return new UnderwritingDecision(false);
        if(medicalQuestions.bmi() > 4)
            return new UnderwritingDecision(false);
        return new UnderwritingDecision(true);
    }
}
