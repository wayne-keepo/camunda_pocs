package com.example.workflow.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static com.example.workflow.Constants.IS_NEGOTIATION_TERMS_CONFIRMED;

@Slf4j
@Component
public class DoSomethingDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("Do something...");
        log.info("business key: {}, flag: {}",
                delegateExecution.getBusinessKey(),
                delegateExecution.getVariable(IS_NEGOTIATION_TERMS_CONFIRMED)
        );
    }
}
