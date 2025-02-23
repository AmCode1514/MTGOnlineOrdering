package com.CS320.app.requests.Handlers;

import io.javalin.http.Context;
import java.lang.reflect.Type;

import com.CS320.app.Exceptions.SchemaException;
import com.CS320.app.requests.Request;

public abstract class RequestHandler {

    protected Type classType;
    protected Request request;
    protected Context ctx;
    // private boolean isPriviledged;

    public RequestHandler(Type classType, Context ctx) {
        this.classType = classType;
        this.ctx = ctx;
    }

    public abstract void validateAgainstSchema() throws SchemaException;
    //implement functionality including class reflection here. The goal is to abstract away individual methods that handle http requests with dependency injection
    protected abstract Request buildRequest();

    protected abstract void setHeaders();

    // public boolean getPriviledgedStatus() {
    //     return isPriviledged;
    // }

    public Request getRequest() {
        if (request == null) {
            request = buildRequest();
        }
        return request;
    }
}