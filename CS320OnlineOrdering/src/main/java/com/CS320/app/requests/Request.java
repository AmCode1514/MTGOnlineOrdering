package com.CS320.app.requests;

public abstract class Request{

    protected String requestType;
    protected String ip;

    public abstract Response buildResponse();
    public void setIP(String ip) {
        this.ip = ip;
    }
}
