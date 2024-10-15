package com.example.workflow.service;


import org.camunda.bpm.engine.*;
import org.jvnet.hk2.annotations.Service;

@Service
public class CamundaProcessingService {
    private final ProcessEngine pe;

    public CamundaProcessingService() {
        this.pe = ProcessEngines.getDefaultProcessEngine();
    }


    public RuntimeService runtimeService() {
        return pe.getRuntimeService();
    }

    public TaskService taskService() {
        return pe.getTaskService();
    }

    public ManagementService managementService() {
        return pe.getManagementService();
    }

}