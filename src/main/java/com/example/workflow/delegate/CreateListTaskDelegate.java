package com.example.workflow.delegate;


import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class CreateListTaskDelegate implements JavaDelegate {
    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        var lsit = new ArrayList<Object>();
        lsit.add("KEKUS");
        delegateExecution.setVariable("list", lsit);

//        delegateExecution.setVariable("list", new ArrayList<Object>());
    }
}
