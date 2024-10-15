package com.example.workflow.controller;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController("/incidents")
public class CamundaIncidentController {
    @Autowired
    private RuntimeService runtimeService;

    @GetMapping("/status/{instanceId}")
    public @ResponseBody String getStatus(@PathVariable String instanceId) {
        List<ProcessInstance> list = runtimeService.createProcessInstanceQuery()
                .processInstanceId(instanceId)
                .list();
        String response = "isEmpty: " + list.isEmpty() + " size: " + list.size() + " process instance ids: " +
                list.stream()
                        .map(Execution::getProcessInstanceId)
                        .collect(Collectors.toList())
                        .toString() + "\n" +
                runtimeService.createIncidentQuery()
                        .processInstanceId(instanceId)
                        .list().toString();
        return response;

    }
}
