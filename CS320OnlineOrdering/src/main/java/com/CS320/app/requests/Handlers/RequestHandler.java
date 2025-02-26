package com.CS320.app.requests.Handlers;

import io.javalin.http.Context;
import java.lang.reflect.Type;

import com.CS320.app.Exceptions.SchemaException;
import com.CS320.app.misc.ServerResourcePackage;
import com.CS320.app.requests.Request;
import com.CS320.app.requests.Response;

public abstract class RequestHandler {

    protected Type classType;
    protected Request request;
    protected Response response;
    protected Context ctx;
    protected ServerResourcePackage pkg;
    // private boolean isPriviledged;

    public RequestHandler(Type classType, Context ctx) {
        this.classType = classType;
        this.ctx = ctx;
    }
    public RequestHandler(Type classType) {

    }
    public void addContext(Context ctx) {
        this.ctx = ctx;
    }

    public abstract void validateAgainstSchema() throws SchemaException;
    //implement functionality including class reflection here. The goal is to abstract away individual methods that handle http requests with dependency injection
    protected abstract void buildRequest();

    //called after the building of the response
    public abstract void setHeaders();

    protected abstract void buildRequestResponse();

    public abstract byte getRequiredResources();

    //assume this function is called before a response is built, failure to do so is undefined behavior.
    public RequestHandler injectRequiredResources(ServerResourcePackage pkg) {
        if (request == null) {
            buildRequest();
        }
        request.injectResourcePackage(pkg);
        return this;
    }

    // public boolean getPriviledgedStatus() {
    //     return isPriviledged;
    // }
    public Response getResponse() {
        if (response == null && request == null) {
            buildRequest();
            buildRequestResponse();
        }
        else if (response == null) {
            buildRequestResponse();
        }
        return response;
    }
    public Request getRequest() {
        if (request == null) {
            buildRequest();
        }
        return request;
    }
}