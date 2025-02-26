package com.CS320.app.requests;

import java.lang.reflect.Type;

import com.CS320.app.misc.ServerResourcePackage;
import com.CS320.app.requests.Handlers.RequestHandler;

import io.javalin.http.Context;

public abstract class Request {

    protected String requestType;
    protected String ip;
    protected transient ServerResourcePackage pkg;

    public abstract Response buildResponse();

    public void injectResourcePackage(ServerResourcePackage pkg) {
        this.pkg = pkg;
    }
    
    public void setIP(String ip) {
        this.ip = ip;
    }
}
