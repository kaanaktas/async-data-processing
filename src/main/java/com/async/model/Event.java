package com.async.model;

import java.io.Serializable;
import java.util.StringJoiner;

public class Event implements Serializable {
    private String eventName;
    private String userId;
    private String appId;
    private String productDescription;
    private String installationDateTime;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getInstallationDateTime() {
        return installationDateTime;
    }

    public void setInstallationDateTime(String installationDateTime) {
        this.installationDateTime = installationDateTime;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Event.class.getSimpleName() + "[", "]")
                .add("eventName='" + eventName + "'")
                .add("userId='" + userId + "'")
                .add("appId='" + appId + "'")
                .add("productDescription='" + productDescription + "'")
                .add("installationDateTime='" + installationDateTime + "'")
                .toString();
    }
}
