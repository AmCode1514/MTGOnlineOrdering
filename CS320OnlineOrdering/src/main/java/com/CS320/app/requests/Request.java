package com.CS320.app.requests;

public abstract class Request{

    protected String requestType;
    protected String ip;

    public Response buildResponse() {
        return null;
    }
    public abstract void setIP(String ip);
}
