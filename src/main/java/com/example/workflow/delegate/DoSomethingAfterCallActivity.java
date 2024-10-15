package com.example.workflow.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DoSomethingAfterCallActivity implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("After call CallActivity...");
        var str = (String) delegateExecution.getVariable("FROM_MSG_EVENT");
        log.info("Getting variable from cor msg: {}", str);
    }
}
