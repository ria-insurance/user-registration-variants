package com.ria.experiments.businessprocessdriven.registration.activities;

import com.ria.experiments.businessprocessdriven.registration.dtos.MedicalQuestions;
import com.ria.experiments.businessprocessdriven.registration.dtos.UnderwritingDecision;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface UnderwritingActivity {

    @ActivityMethod
    public UnderwritingDecision doUnderwriting(MedicalQuestions medicalQuestions);

}
