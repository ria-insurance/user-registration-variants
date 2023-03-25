package com.ria.process.api.engine.camunda.workflows.proposal;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;

@Slf4j
public class StoreHealthDetailsDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution execution) throws Exception {
        Integer weight = (Integer) execution.getVariable("weight");
        Integer height = (Integer) execution.getVariable("height");
        Integer age = (Integer) execution.getVariable("age");
        Double heightInMeters = height / 100.0;
        Double bmi = weight / (heightInMeters * heightInMeters);
        execution.setVariable("bmi", bmi);
        log.info("StoreHealthDetailsDelegate :: Age, Height and Weight are {} {} {}",age, height, weight);
        log.info("StoreHealthDetailsDelegate :: Calculated BMI is {}", bmi);

    }
}
