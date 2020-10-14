package com.king.model;

/**
 * Created by kaktas on 14/10/2020
 */
public class Event {
    private final String eventName;
    private final String userId;
    private final int gameId;
    private final long installationDateTime;

    public Event(final String eventName, final String userId, final int gameId, final long installationDateTime) {
        this.eventName = eventName;
        this.userId = userId;
        this.gameId = gameId;
        this.installationDateTime = installationDateTime;
    }

    public String getEventName() {
        return eventName;
    }

    public String getUserId() {
        return userId;
    }

    public int getGameId() {
        return gameId;
    }

    public long getInstallationDateTime() {
        return installationDateTime;
    }
}
