package com.ria.experiments.businessprocessdriven.registration.activities;

import com.ria.experiments.businessprocessdriven.registration.dtos.MedicalQuestions;
import io.temporal.activity.ActivityInterface;
import io.temporal.activity.ActivityMethod;

@ActivityInterface
public interface MedicalQuestionRecording {

    @ActivityMethod
    MedicalQuestions recordMedicalQuestions(MedicalQuestions medicalQuestions);
}
