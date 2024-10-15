package com.example.workflow;

import com.example.workflow.incident.CamundaIncidentHandler;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.ProcessEnginePlugin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;

//@Component
public class CamundaCustomPlugin implements ProcessEnginePlugin {
//    @Autowired
    private CamundaIncidentHandler camundaIncidentHandler;

    @Override
    public void preInit(ProcessEngineConfigurationImpl processEngineConfiguration) {
        processEngineConfiguration.setEnsureJobDueDateNotNull(true);
        processEngineConfiguration.setCustomIncidentHandlers(List.of(camundaIncidentHandler));
    }

    @Override
    public void postInit(ProcessEngineConfigurationImpl processEngineConfiguration) {

    }

    @Override
    public void postProcessEngineBuild(ProcessEngine processEngine) {

    }
}
