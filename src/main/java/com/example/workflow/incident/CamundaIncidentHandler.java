package com.example.workflow.incident;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.incident.IncidentContext;
import org.camunda.bpm.engine.impl.incident.IncidentHandler;
import org.camunda.bpm.engine.runtime.Incident;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CamundaIncidentHandler implements IncidentHandler {
    @Autowired
    private RuntimeService runtimeService;
    @Override
    public String getIncidentHandlerType() {
        return Incident.FAILED_JOB_HANDLER_TYPE;
    }

    @Override
    public Incident handleIncident(IncidentContext incidentContext, String s) {
        log.info(incidentContext.toString());
        return null;
    }

    @Override
    public void resolveIncident(IncidentContext incidentContext) {

    }

    @Override
    public void deleteIncident(IncidentContext incidentContext) {

    }
}
