package com.example.workflow.delegate;

import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.persistence.entity.TimerEntity;
import org.camunda.bpm.engine.runtime.Job;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.logging.Logger;


@Component("timerCatchDelegate")
public class TimerCatchDelegate implements JavaDelegate {
    @Autowired
    private ManagementService managementService;

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("TimerCatchDelegate | ======================================================");
        final LocalDateTime now = LocalDateTime.now();

        Job msgTimeoutTimerJob = managementService
                .createJobQuery()
                .timers()
                .activityId("MsgEventTimeout")
                .list()
                .get(0);

        TimerEntity timer = (TimerEntity) msgTimeoutTimerJob;

        System.out.println("MsgEventTimeout: " + timer.toString());

        LocalDateTime timeout = LocalDateTime.ofInstant(timer.getDuedate().toInstant(), ZoneId.of("Europe/Samara"));
        System.out.println("jobDefId: " + timer.getJobDefinitionId());
        System.out.println("jobDef: " + timer.getJobDefinition().toString());
        System.out.println("timer duedate: " + timeout.toString());
        System.out.println("now: " + now.toString());
        System.out.println("catch timeout? " + now.isAfter(timeout));

        System.out.println("====================================================================================================");

    }
}
