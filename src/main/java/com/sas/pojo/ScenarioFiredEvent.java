package com.sas.pojo;

public class ScenarioFiredEvent {
    private String alertingEventId;
    private String scenarioFiredEventId;
    private String scenarioId;
    private String scenarioName;
    private String scenarioOriginCode;
    private String displayFlg;
    private String displayTypeCode;
    private String scenarioDescription;
    private Integer score;

    public ScenarioFiredEvent() {
    }

    public ScenarioFiredEvent(String alertingEventId, String scenarioFiredEventId, String scenarioId, String scenarioName, String scenarioOriginCode, String displayFlg, String displayTypeCode, String scenarioDescription, Integer score) {
        this.alertingEventId = alertingEventId;
        this.scenarioFiredEventId = scenarioFiredEventId;
        this.scenarioId = scenarioId;
        this.scenarioName = scenarioName;
        this.scenarioOriginCode = scenarioOriginCode;
        this.displayFlg = displayFlg;
        this.displayTypeCode = displayTypeCode;
        this.scenarioDescription = scenarioDescription;
        this.score = score;
    }

    public String getAlertingEventId() {
        return alertingEventId;
    }

    public void setAlertingEventId(String alertingEventId) {
        this.alertingEventId = alertingEventId;
    }

    public String getScenarioFiredEventId() {
        return scenarioFiredEventId;
    }

    public void setScenarioFiredEventId(String scenarioFiredEventId) {
        this.scenarioFiredEventId = scenarioFiredEventId;
    }

    public String getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(String scenarioId) {
        this.scenarioId = scenarioId;
    }

    public String getScenarioName() {
        return scenarioName;
    }

    public void setScenarioName(String scenarioName) {
        this.scenarioName = scenarioName;
    }

    public String getScenarioOriginCode() {
        return scenarioOriginCode;
    }

    public void setScenarioOriginCode(String scenarioOriginCode) {
        this.scenarioOriginCode = scenarioOriginCode;
    }

    public String getDisplayFlg() {
        return displayFlg;
    }

    public void setDisplayFlg(String displayFlg) {
        this.displayFlg = displayFlg;
    }

    public String getDisplayTypeCode() {
        return displayTypeCode;
    }

    public void setDisplayTypeCode(String displayTypeCode) {
        this.displayTypeCode = displayTypeCode;
    }

    public String getScenarioDescription() {
        return scenarioDescription;
    }

    public void setScenarioDescription(String scenarioDescription) {
        this.scenarioDescription = scenarioDescription;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }
}
