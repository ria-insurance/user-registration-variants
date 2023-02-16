package com.ria.experiments.businessprocessdriven.registration.client;

import com.ria.experiments.businessprocessdriven.registration.Shared;
import com.ria.experiments.businessprocessdriven.registration.dtos.MedicalQuestions;
import com.ria.experiments.businessprocessdriven.registration.workflow.SendVerificationEmail;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import io.temporal.serviceclient.WorkflowServiceStubs;

public class ClickOnVerified {
    public static void main(String[] args) {

        WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
        WorkflowClient client = WorkflowClient.newInstance(service);
        // WorkflowStubs enable calls to methods as if the Workflow object is local, but actually perform an RPC.
        // var workflow = client.newWorkflowStub(AskMedicalQuestions.class, options);



        WorkflowStub untypedWorkflowStub = client.newUntypedWorkflowStub("SendVerificationEmail",
                WorkflowOptions.newBuilder()
                        .setWorkflowId("email-verification-workflow")
                        .setTaskQueue(Shared.USER_REGISTRATION_TASK_QUEUE)
                        .build());


        untypedWorkflowStub.signalWithStart("emailVerified", new Object[] {"a@test.com"}, new Object[] {});

    }
}
