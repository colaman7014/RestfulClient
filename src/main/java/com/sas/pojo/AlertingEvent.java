package com.sas.pojo;

import java.util.List;

public class AlertingEvent {
    private String alertingEventId;
    private String actionableEntityType;
    private String actionableEntityId;
    private String alertOriginCode;
    private String alertTypeCode;
    private String alertTriggerTxt;
    private String modelName;
    private String recommendedQueueId;
    private String scoreDescription;
    private Integer score;

    public String getAlertingEventId() {
        return alertingEventId;
    }

    public void setAlertingEventId(String alertingEventId) {
        this.alertingEventId = alertingEventId;
    }

    public String getActionableEntityType() {
        return actionableEntityType;
    }

    public void setActionableEntityType(String actionableEntityType) {
        this.actionableEntityType = actionableEntityType;
    }

    public String getActionableEntityId() {
        return actionableEntityId;
    }

    public void setActionableEntityId(String actionableEntityId) {
        this.actionableEntityId = actionableEntityId;
    }

    public String getAlertOriginCode() {
        return alertOriginCode;
    }

    public void setAlertOriginCode(String alertOriginCode) {
        this.alertOriginCode = alertOriginCode;
    }

    public String getAlertTypeCode() {
        return alertTypeCode;
    }

    public void setAlertTypeCode(String alertTypeCode) {
        this.alertTypeCode = alertTypeCode;
    }

    public String getAlertTriggerTxt() {
        return alertTriggerTxt;
    }

    public void setAlertTriggerTxt(String alertTriggerTxt) {
        this.alertTriggerTxt = alertTriggerTxt;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getRecommendedQueueId() {
        return recommendedQueueId;
    }

    public void setRecommendedQueueId(String recommendedQueueId) {
        this.recommendedQueueId = recommendedQueueId;
    }

    public String getScoreDescription() {
        return scoreDescription;
    }

    public void setScoreDescription(String scoreDescription) {
        this.scoreDescription = scoreDescription;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

}
