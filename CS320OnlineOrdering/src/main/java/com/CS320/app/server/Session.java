package com.CS320.app.server;

public class Session {
    private String ip;
    private String token;
    private String email;
    private int priority;
    private long time;
    public Session(String ip, String token, String email) {
        this.ip = ip;
        this.token = token;
        this.email = email;
        this.priority = 1;
        this.time = System.currentTimeMillis();
    }
    public Session(String email, String token) {
        this.email = email;
        this.token = token;
    }
    public int getPriority() {
        return priority;
    }
    public long timeSinceActivity() {
        return System.currentTimeMillis() - time;
    }
    public void updateActivity() {
        time = System.currentTimeMillis();
    }
    @Override
    public int hashCode() {
        return email.hashCode() * token.hashCode();
    }
}
