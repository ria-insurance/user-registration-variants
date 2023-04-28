package com.ria.experiments.businessprocessdriven.registration.workflow;

import com.ria.experiments.businessprocessdriven.registration.activities.MedicalQuestionRecording;
import com.ria.experiments.businessprocessdriven.registration.activities.UnderwritingActivity;
import com.ria.experiments.businessprocessdriven.registration.dtos.MedicalQuestions;
import com.ria.experiments.businessprocessdriven.registration.dtos.UnderwritingDecision;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.Objects;

@Slf4j
public class AskMedicalQuestionsWorkflow implements AskMedicalQuestions {

    MedicalQuestions medicalQuestions;

    private final RetryOptions retryoptions = RetryOptions.newBuilder()
            .setInitialInterval(Duration.ofSeconds(1))
            .setMaximumInterval(Duration.ofSeconds(100))
            .setBackoffCoefficient(2)
            .setMaximumAttempts(500)
            .build();

    private final ActivityOptions defaultActivityOptions = ActivityOptions.newBuilder()
            // Timeout options specify when to automatically timeout Activities if the process is taking too long.
            .setStartToCloseTimeout(Duration.ofSeconds(5))
            // Optionally provide customized RetryOptions.
            // Temporal retries failures by default, this is simply an example.
            .setRetryOptions(retryoptions)
            .build();

    MedicalQuestionRecording medicalQuestionRecording = Workflow.newActivityStub(MedicalQuestionRecording.class, defaultActivityOptions);
    UnderwritingActivity underwritingActivity = Workflow.newActivityStub(UnderwritingActivity.class, defaultActivityOptions);

    @Override
    public UnderwritingDecision askMedicalQuestions() {
        log.warn("Asking medical questions. Age and BMI");
        Workflow.await(() -> Objects.nonNull(medicalQuestions));
        MedicalQuestions medicalQuestionsAnswers = medicalQuestionRecording.recordMedicalQuestions(medicalQuestions);
        UnderwritingDecision underwritingDecision = underwritingActivity.doUnderwriting(medicalQuestionsAnswers);
        log.info("Underwriting decision : " + underwritingDecision);
        return underwritingDecision;
    }

    @Override
    public void answerMedicalQuestions(MedicalQuestions medicalQuestions) {
        log.info("Received medical answers : " + medicalQuestions);
        this.medicalQuestions = medicalQuestions;
    }
}
