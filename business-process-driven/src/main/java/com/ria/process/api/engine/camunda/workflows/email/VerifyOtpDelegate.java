package com.ria.process.api.engine.camunda.workflows.email;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;


@Slf4j
public class VerifyOtpDelegate implements JavaDelegate {

    public void execute(DelegateExecution execution) throws Exception {
        String receivedOtp = (String) execution.getVariable("receivedOtp");
        log.info("Verifying OTP for " + execution.getVariable("email") );
        String sentOtp = (String) execution.getVariable("sentOtp");
        if(receivedOtp.equals(sentOtp)) {
            execution.setVariable("otpVerified", "true");
            log.info("OTP verified for " + execution.getVariable("email") );

        }
        else {
            execution.setVariable("otpVerified", "false");
            log.info("Invalid OTP for " + execution.getVariable("email") );
        }
    }

}