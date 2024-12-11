package com.example.workflow;

import org.jboss.logging.MDC;

public class Constants {
    public static final String PROCESS_NAME = "my-project-process";

    public static final String PROCESS_SIGNAL_EVENT_TEST = "PROCESS_SIGNAL_EVENT_TEST";

    public static final String CORRELATE_MSG = "TestMessage";

    public static final String MainProcessTestCorrelationMsg = "MainProcessTestCorrelationMsg";
    
    public static final String SubProcessTestCorrelationMsg = "SubProcessTestCorrelationMsg";

    public static final String ThrowingNonInterTimeoutWhileAwaitingMsg = "ThrowingNonInterTimeoutWhileAwaitingMsg";

    public static void initMdc(String businessKey) {
        MDC.put("BUSINESS_KEY", businessKey);
    }

    public static void clearMdc() {
        MDC.clear();
    }

    public static final String IS_NEGOTIATION_TERMS_CONFIRMED = "isNegotiationTermsConfirmed";

    public static final String MULTI_PROCESSING_SIGNAL_EVENT = "MultiProcessSignalEvent";
}
