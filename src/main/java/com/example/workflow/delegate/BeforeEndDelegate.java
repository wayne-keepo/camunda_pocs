package com.example.workflow.delegate;

import org.camunda.bpm.engine.ManagementService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.runtime.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BeforeEndDelegate implements JavaDelegate {
    @Autowired
    private ManagementService managementService;


    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        System.out.println("BeforeEndDelegate ========================");
        List<Job> allTimers = managementService
                .createJobQuery()
                .timers()
                .list();


        System.out.println("all timers: \n" + allTimers);

        System.out.println( "active timers:\n" +
                managementService.createJobQuery().timers().active().list()
        );

        System.out.println( "timer by activity id MsgEventTimeout: \n" +
                managementService.createJobQuery().timers().activityId("MsgEventTimeout").list()
        );

        System.out.println("activity instance id: " + delegateExecution.getActivityInstanceId());

        System.out.println("getProcessInstanceId: " + delegateExecution.getProcessInstanceId());
        System.out.println("getBusinessKey: " + delegateExecution.getBusinessKey());
        System.out.println("getProcessBusinessKey: " + delegateExecution.getProcessBusinessKey());
        System.out.println("====================================================================================================");

    }
}
