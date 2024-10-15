package com.example.workflow.delegate;

import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.persistence.entity.TimerEntity;
import org.camunda.bpm.engine.runtime.Job;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Component("msgCatchDelegate")
public class MsgCatchDelegate implements JavaDelegate {

    @Autowired
    private ManagementService managementService;
    @Autowired
    private RuntimeService runtimeService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("MsgCatchDelegate | Msg catching =================================================");

        final LocalDateTime now = LocalDateTime.now();

        ProcessInstance pils = runtimeService.createProcessInstanceQuery()
                .processDefinitionKey("my-project-process")
                .processInstanceBusinessKey("kekus-123")
                .singleResult();

        System.out.println(pils);
        System.out.println(pils.getProcessInstanceId());


        Job msgTimeoutTimerJob = managementService
                .createJobQuery()
                .timers()
                .processInstanceId(pils.getProcessInstanceId())
                .activityId("MsgEventTimeout")
                .singleResult();

        TimerEntity timer = (TimerEntity) msgTimeoutTimerJob;

        System.out.println("MsgEventTimeout: " + timer.toString());

        LocalDateTime timeout = LocalDateTime.ofInstant(timer.getDuedate().toInstant(), ZoneId.of("Europe/Samara"));

        System.out.println("timer duedate: " + timeout.toString());
        System.out.println("now: " + now.toString());
        System.out.println("timeout not expire? " + now.isAfter(timeout));

        System.out.println("getProcessDefinitionKey : " + msgTimeoutTimerJob.getProcessDefinitionKey());
        System.out.println("getProcessInstanceId : " + msgTimeoutTimerJob.getProcessInstanceId());
        System.out.println("getProcessDefinitionId : " + msgTimeoutTimerJob.getProcessDefinitionId());

        System.out.println("====================================================================================================");


    }
}
