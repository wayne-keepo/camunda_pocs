package com.example.workflow.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DoSomethingWhenCorrelateMainProcMsg implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        log.info("Do something when call correlation message into main process with defId " +
                 delegateExecution.getProcessDefinitionId()
        );
        delegateExecution.setVariable("FROM_MSG_EVENT", "ADDED_FROM_MAIN_COR_PROC");
    }
}