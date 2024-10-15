package com.example.workflow.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class ShowListValuesTaskDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var list = delegateExecution.getVariable("testList");
        var firstValue = delegateExecution.getVariable("firstListValue");


        System.out.printf(list.toString());
        System.out.printf(firstValue.toString());

    }
}
