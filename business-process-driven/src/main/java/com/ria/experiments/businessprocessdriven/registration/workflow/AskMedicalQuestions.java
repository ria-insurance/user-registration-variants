package com.ria.experiments.businessprocessdriven.registration.workflow;

import com.ria.experiments.businessprocessdriven.registration.dtos.MedicalQuestions;
import com.ria.experiments.businessprocessdriven.registration.dtos.UnderwritingDecision;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface AskMedicalQuestions {

    @WorkflowMethod
    UnderwritingDecision askMedicalQuestions();
    @SignalMethod
    void answerMedicalQuestions(MedicalQuestions medicalQuestions);

}
