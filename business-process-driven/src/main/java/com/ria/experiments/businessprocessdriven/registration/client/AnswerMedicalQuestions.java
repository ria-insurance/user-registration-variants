package com.ria.experiments.businessprocessdriven.registration.client;

import com.ria.experiments.businessprocessdriven.registration.Shared;
import com.ria.experiments.businessprocessdriven.registration.dtos.MedicalQuestions;
import com.ria.experiments.businessprocessdriven.registration.workflow.AskMedicalQuestions;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import io.temporal.serviceclient.WorkflowServiceStubs;

public class AnswerMedicalQuestions {

    public static void main(String[] args) {
        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowOptions options = WorkflowOptions.newBuilder()
                .setTaskQueue(Shared.USER_REGISTRATION_TASK_QUEUE)
                // A WorkflowId prevents this it from having duplicate instances, remove it to duplicate.
                .setWorkflowId("medical-questions-workflow")
                .build();
        // WorkflowClient can be used to start, signal, query, cancel, and terminate Workflows.
        WorkflowClient client = WorkflowClient.newInstance(service);
        // WorkflowStubs enable calls to methods as if the Workflow object is local, but actually perform an RPC.
       // var workflow = client.newWorkflowStub(AskMedicalQuestions.class, options);

        WorkflowStub untypedWorkflowStub = client.newUntypedWorkflowStub("AskMedicalQuestions",
                WorkflowOptions.newBuilder()
                        .setWorkflowId("user-registration-workflow")
                        .setTaskQueue(Shared.USER_REGISTRATION_TASK_QUEUE)
                        .build());

        MedicalQuestions medicalQuestions = new MedicalQuestions(38, 3);

        untypedWorkflowStub.signalWithStart("answerMedicalQuestions", new Object[] {medicalQuestions}, new Object[] {});

        // start the Workflow
        //WorkflowClient.start(workflow::askMedicalQuestions);

       // workflow.answerMedicalQuestions(medicalQuestions);
    }
}
