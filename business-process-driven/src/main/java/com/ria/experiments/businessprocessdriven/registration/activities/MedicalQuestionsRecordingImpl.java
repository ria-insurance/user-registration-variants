package com.ria.experiments.businessprocessdriven.registration.activities;

import com.ria.experiments.businessprocessdriven.registration.dtos.MedicalQuestions;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MedicalQuestionsRecordingImpl implements MedicalQuestionRecording{
    @Override
    public MedicalQuestions recordMedicalQuestions(MedicalQuestions medicalQuestions) {
        log.info("Medical questions answers : "+medicalQuestions);
        return medicalQuestions;
    }
}
