package com.ria.process.api.engine.camunda;



import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

@Slf4j
public class SendOtpDelegate implements JavaDelegate {

    public void execute(DelegateExecution execution) throws Exception {
        execution.setVariable("sentOtp","123456");
        log.info("Sending OTP to " + execution.getVariable("email") + " : 123456");
    }

}