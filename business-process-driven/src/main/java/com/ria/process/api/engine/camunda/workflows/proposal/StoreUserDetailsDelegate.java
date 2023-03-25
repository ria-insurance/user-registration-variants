package com.ria.process.api.engine.camunda.workflows.proposal;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

@Slf4j
public class StoreUserDetailsDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {

        String name = (String) execution.getVariable("name");
        String email = (String) execution.getVariable("email");
        String city = (String) execution.getVariable("city");
        log.info("StoreUserDetailsDelegate :: Name, email and city are {} {} {}", name, email, city);


    }
}
