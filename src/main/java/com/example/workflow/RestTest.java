package com.example.workflow;

import com.example.workflow.service.CamundaProcessingService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.workflow.Constants.*;

@Slf4j
@RestController
@RequestMapping(value = "/poc/")
public class RestTest {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private CamundaProcessingService processingService;

    @PostMapping
    public ResponseEntity<ProcessInstanceInfo> runProcess(@RequestBody StartProcessMsg msg) {

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(PROCESS_NAME, msg.getId());

        var pii = new ProcessInstanceInfo(processInstance.getProcessDefinitionId(), processInstance.getProcessInstanceId(), processInstance.getBusinessKey(), null);

        return new ResponseEntity<ProcessInstanceInfo>(pii, HttpStatus.CREATED);
    }

    @GetMapping(value = "correlate/{businessKey}")
    public HttpStatus recievMessage(@PathVariable String businessKey) {
        try {
            initMdc(businessKey);
            log.info("start correlate message by {}", businessKey);
            List<ProcessInstance> processInstances = runtimeService
                    .createProcessInstanceQuery()
                    .processDefinitionKey(PROCESS_NAME)
                    .processInstanceBusinessKey(businessKey)
                    .active()
                    .list();
            log.info("active instances:\n{}", processInstances);
//            Execution execution = runtimeService.createExecutionQuery()
//                    .processInstanceBusinessKey(businessKey)
//                    .list().get(0);
//
//            Map<String, Object> variables = runtimeService.getVariables(execution.getId());
//
//            if ((Boolean) variables.getOrDefault("timeout", false)) return HttpStatus.ACCEPTED;

            runtimeService
                    .createMessageCorrelation(CORRELATE_MSG)
                    .correlate();

            return HttpStatus.OK;
        } finally {
            clearMdc();
        }
    }

    @GetMapping(value = "info/{businessKey}")
    public List<ProcessInstanceInfo> getInfoByBusinessKey(@PathVariable String businessKey) {
        var list = processingService.runtimeService()
                .createProcessInstanceQuery()
                .processInstanceBusinessKey(businessKey)
                .active()
                .list();

        return list.stream().map(pi -> new ProcessInstanceInfo(pi.getProcessDefinitionId(), pi.getId(), pi.getBusinessKey(), pi.getRootProcessInstanceId())).collect(Collectors.toList());

    }

    @GetMapping(value = "correlate/choice/{businessKey}")
    public HttpStatus correlateWithChoiceByBusinessKey(@PathVariable String businessKey) {
        List<ProcessInstance> activeProcessInstances = processingService.runtimeService()
                .createProcessInstanceQuery()
                .processInstanceBusinessKey(businessKey)
                .active().list();

        activeProcessInstances
                .stream()
                .filter((ProcessInstance pi) -> pi.getId() != pi.getRootProcessInstanceId())
                .findFirst()
                .ifPresentOrElse(
                        sp -> {
                            log.info("Found subProcess: " + sp.getProcessDefinitionId() + " that called from root: " + sp.getRootProcessInstanceId());
                            processingService
                                    .runtimeService()
                                    .createMessageCorrelation(SubProcessTestCorrelationMsg)
                                    .correlate();
                        },
                        () -> {
                            activeProcessInstances
                                    .stream()
                                    .filter((ProcessInstance pi) -> pi.getId() == pi.getRootProcessInstanceId())
                                    .findFirst()
                                    .ifPresent(
                                            pi -> {
                                                log.info("Not found subProcess, will correlate into main process: " + pi.getId());
                                                processingService
                                                        .runtimeService()
                                                        .createMessageCorrelation(MainProcessTestCorrelationMsg)
                                                        .correlate();
                                            }
                                    );
                        }
                );

        return HttpStatus.ACCEPTED;
    }


    @RequiredArgsConstructor
    @Data
    static class StartProcessMsg {
        private String id;
    }

    @AllArgsConstructor
    @Data
    static class ProcessInstanceInfo {
        private String name;
        private String id;
        private String businessKey;
        private String rootProcessInstId;
    }
}
