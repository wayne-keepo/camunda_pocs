package com.example.workflow.delegate;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static com.example.workflow.Constants.IS_NEGOTIATION_TERMS_CONFIRMED;

@Component
public class UpdContextVarDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        delegateExecution.setVariable(IS_NEGOTIATION_TERMS_CONFIRMED, true);
    }
}
