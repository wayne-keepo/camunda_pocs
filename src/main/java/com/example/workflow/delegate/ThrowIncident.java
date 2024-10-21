package com.example.workflow.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Component
public class ThrowIncident implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        throw new RuntimeException("Чо-то пошло не по плану...");
    }
}
