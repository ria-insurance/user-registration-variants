package com.ria.process.api.engine.camunda.workflows.email;



import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

import java.util.Objects;

@Slf4j
public class SendOtpDelegate implements JavaDelegate {

    public void execute(DelegateExecution execution) throws Exception {
        String email = (String) execution.getVariable("email");
        Objects.nonNull(email);
        log.info("Sending OTP to " + email + " : 123456");
        execution.setVariable("sentOtp","123456");
    }

}