package com.ria.experiments.businessprocessdriven.controllers;

import com.ria.experiments.businessprocessdriven.registration.Shared;
import com.ria.experiments.businessprocessdriven.registration.dtos.MedicalQuestions;
import com.ria.experiments.businessprocessdriven.registration.dtos.UnverifiedRegistrationEmail;
import com.ria.process.api.engine.camunda.EmailVerificationProcessHelper;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import io.temporal.serviceclient.WorkflowServiceStubs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserActionController {

    @Autowired
    EmailVerificationProcessHelper emailVerificationProcessHelper;

    WorkflowServiceStubs service = WorkflowServiceStubs.newLocalServiceStubs();
    WorkflowClient client = WorkflowClient.newInstance(service);


    @PostMapping("enter-otp")
    public ResponseEntity<Void> enterOtp(@RequestBody EnterOtpRequest enterOtpRequest) {
        emailVerificationProcessHelper.enterOtp(enterOtpRequest.otp(), enterOtpRequest.processId());
        return new ResponseEntity(null, HttpStatus.OK);
    }


    @PostMapping("medical-details")
    public ResponseEntity<Void> addMedicalDetails(@RequestBody MedicalQuestions medicalQuestions) {
        WorkflowStub untypedWorkflowStub = client.newUntypedWorkflowStub("AskMedicalQuestions",
                WorkflowOptions.newBuilder()
                        .setWorkflowId("medical-questions-workflow")
                        .setTaskQueue(Shared.USER_REGISTRATION_TASK_QUEUE)
                        .build());

        untypedWorkflowStub.signalWithStart("answerMedicalQuestions", new Object[] {medicalQuestions}, new Object[] {});
        return new ResponseEntity(null, HttpStatus.CREATED);
    }

    @PostMapping("verify-email")
    public ResponseEntity<Void> verifyEmail(@RequestBody UnverifiedRegistrationEmail unverifiedRegistrationEmail) {
        WorkflowStub untypedWorkflowStub = client.newUntypedWorkflowStub("SendVerificationEmail",
                WorkflowOptions.newBuilder()
                        .setWorkflowId("email-verification-workflow")
                        .setTaskQueue(Shared.USER_REGISTRATION_TASK_QUEUE)
                        .build());


        untypedWorkflowStub.signalWithStart("emailVerified", new Object[] {unverifiedRegistrationEmail.email()}, new Object[] {});
        return new ResponseEntity(null, HttpStatus.CREATED);
    }
}
