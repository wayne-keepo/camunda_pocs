package com.example.workflow.delegate;


import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DoSomethingBeforeCallActivity implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var bk = delegateExecution.getProcessBusinessKey();
        log.info("Befor call CallActivity with BK: " + bk + "...");
    }
}