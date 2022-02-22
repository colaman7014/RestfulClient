package com.sas.pojo;

import java.util.List;

public class AlertEvent {
    private List<AlertingEvent> alertingEvents;
    private List<ScenarioFiredEvent> scenarioFiredEvents;

    public List<ScenarioFiredEvent> getScenarioFiredEvents() {
        return scenarioFiredEvents;
    }

    public void setScenarioFiredEvents(List<ScenarioFiredEvent> scenarioFiredEvents) {
        this.scenarioFiredEvents = scenarioFiredEvents;
    }

    public List<AlertingEvent> getAlertingEvents() {
        return alertingEvents;
    }

    public void setAlertingEvents(List<AlertingEvent> alertingEvents) {
        this.alertingEvents = alertingEvents;
    }
}
