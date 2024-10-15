package com.example.workflow.delegate;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Slf4j
@Component("incidentThrowingDelegate")
public class IncidentThrowingDelegate implements JavaDelegate {

    @Autowired
    private ManagementService managementService;

    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery()
                .processDefinitionKey("my-project-process")
                .processInstanceBusinessKey("kekus-123")
                .singleResult();
        log.info("ProcessInstance when before throwing incident: {}", processInstance);
        throw new RuntimeException("Упали в инцидент ждем чо делать");
    }
}

//2023-08-18 15:39:05.581 [camundaTaskExecutor-1] [activityId=Activity_07dhdxk, processDefinitionId=my-project-process:1:4a9d5496-3dba-11ee-8833-a0294274d6d4, processInstanceId=d61f7bab-3dbb-11ee-8833-a0294274d6d4, activityName=Упасть в инцидент, engineName=default] INFO  c.e.w.delegate.IncidentThrowingDelegate - ProcessInstance when before throwing incident: ProcessInstance[d61f7bab-3dbb-11ee-8833-a0294274d6d4]