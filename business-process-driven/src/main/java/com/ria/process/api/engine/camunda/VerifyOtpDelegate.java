package com.ria.process.api.engine.camunda;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;


@Slf4j
public class VerifyOtpDelegate implements JavaDelegate {

    public void execute(DelegateExecution execution) throws Exception {
        String receivedOtp = (String) execution.getVariable("receivedOtp");
        String sentOtp = (String) execution.getVariable("sentOtp");
        if(receivedOtp.equals(sentOtp))
            execution.setVariable("otpVerified","true");
        else
            execution.setVariable("otpVerified","false");
        log.info("Verifying OTP for " + execution.getVariable("email") );
    }

}