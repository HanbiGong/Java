package com.apd545.hotel;

public class ActivityRow {

    private String timestamp;
    private String user;
    private String action;
    private String details;

    public ActivityRow(String timestamp, String user, String action, String details) {
        this.timestamp = timestamp;
        this.user = user;
        this.action = action;
        this.details = details;
    }

    // simple getters for table view
    public String getTimestamp() { return timestamp; }
    public String getUser() { return user; }
    public String getAction() { return action; }
    public String getDetails() { return details; }
}
