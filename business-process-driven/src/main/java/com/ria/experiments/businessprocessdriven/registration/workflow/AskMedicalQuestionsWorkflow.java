package com.ria.experiments.businessprocessdriven.registration.workflow;

import com.ria.experiments.businessprocessdriven.registration.dtos.MedicalQuestions;
import com.ria.experiments.businessprocessdriven.registration.dtos.UnderwritingDecision;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;

@Slf4j
public class AskMedicalQuestionsWorkflow implements AskMedicalQuestions{

    MedicalQuestions medicalQuestions;
    @Override
    public UnderwritingDecision askMedicalQuestions() {
        log.warn("Asking medical questions. Age and BMI");
        while (true) {
            Workflow.await(() -> Objects.nonNull(medicalQuestions));
            if(medicalQuestions.age() > 60)
                return new UnderwritingDecision(false);
            if(medicalQuestions.bmi() > 4)
                return new UnderwritingDecision(false);
            return new UnderwritingDecision(true);
        }
    }

    @Override
    public void answerMedicalQuestions(MedicalQuestions medicalQuestions) {
        log.info("Received medical answers : "+medicalQuestions);
        this.medicalQuestions = medicalQuestions;
    }
}
