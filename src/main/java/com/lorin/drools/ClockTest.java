package com.lorin.drools;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

/**
 * Created by lorin on 16/7/13.
 */
public class ClockTest {
    public static void main(String[] args) {
        KieServices ks = KieServices.Factory.get();
        KieContainer kieContainer = ks.getKieClasspathContainer();
        KieSession kieSession = kieContainer.newKieSession("session-clock");
        kieSession.insert(new Clock());
        kieSession.fireAllRules();
        kieSession.dispose();
    }
}
