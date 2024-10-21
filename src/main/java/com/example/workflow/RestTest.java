package com.example.workflow;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.workflow.Constants.*;

@Slf4j
@RestController
@RequestMapping(value = "/poc/")
public class RestTest {
    @Autowired
    private RuntimeService runtimeService;

    @PostMapping
    public ResponseEntity<ProcessInstanceInfo> runProcess(@RequestBody StartProcessMsg msg) {

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(
                msg.getProcessDefenitionName(), msg.getBusinessKey()
        );

        var pii = new ProcessInstanceInfo(
                processInstance.getProcessDefinitionId(),
                processInstance.getProcessInstanceId(),
                processInstance.getBusinessKey(),
                null
        );

        return new ResponseEntity<ProcessInstanceInfo>(pii, HttpStatus.CREATED);
    }

    @GetMapping(value = "info/{businessKey}")
    public List<ProcessInstanceInfo> getInfoByBusinessKey(@PathVariable String businessKey) {
        var list = runtimeService
                .createProcessInstanceQuery()
                .processInstanceBusinessKey(businessKey)
                .active()
                .list();

        return list.stream().map(pi -> new ProcessInstanceInfo(pi.getProcessDefinitionId(), pi.getId(), pi.getBusinessKey(), pi.getRootProcessInstanceId())).collect(Collectors.toList());

    }

    @GetMapping(value = "correlate/{msg}")
    public HttpStatus correlate(@PathVariable String msg) {

        runtimeService.correlateMessage(msg);

        return HttpStatus.OK;
    }

    @PostMapping(value = "correlate")
    public HttpStatus correlate(@RequestBody CorrelationInfo correlationInfo) {

        runtimeService
                .createMessageCorrelation(correlationInfo.message)
                .processInstanceBusinessKey(correlationInfo.businessKey)
                .setVariables(correlationInfo.variables)
                .correlate();

        return HttpStatus.OK;
    }

    @GetMapping(value = "correlate/choice/{businessKey}")
    public HttpStatus correlateWithChoiceByBusinessKey(@PathVariable String businessKey) {
        List<ProcessInstance> activeProcessInstances = runtimeService
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
                            runtimeService
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
                                                runtimeService
                                                        .createMessageCorrelation(MainProcessTestCorrelationMsg)
                                                        .correlate();
                                            }
                                    );
                        }
                );

        return HttpStatus.ACCEPTED;
    }


    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class StartProcessMsg {
        private String processDefenitionName;
        private String businessKey;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class ProcessInstanceInfo {
        private String name;
        private String id;
        private String businessKey;
        private String rootProcessInstId;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    static class CorrelationInfo {
        private String businessKey;
        private String message;
        private Map<String, Object> variables;
    }
}
