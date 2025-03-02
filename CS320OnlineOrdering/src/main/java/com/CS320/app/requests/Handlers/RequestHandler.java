package com.CS320.app.requests.Handlers;

import io.javalin.http.Context;
import java.lang.reflect.Type;
import java.util.Map;

import com.CS320.app.Exceptions.SchemaException;
import com.CS320.app.misc.ServerResourcePackage;
import com.CS320.app.requests.Requests.Request;
import com.CS320.app.requests.Responses.Response;

public abstract class RequestHandler {

    protected Type classType;
    protected Request request;
    protected Response response;
    protected Context ctx;
    protected ServerResourcePackage pkg;
    protected final Map<String,String> headers;
    // private boolean isPriviledged;

    public RequestHandler(Type classType, Context ctx) {
        this.classType = classType;
        this.ctx = ctx;
        //I will have to dig into the implementation of this function. If this map is built for every context it doesn't meaningfully contribute to runtime.
        //If not, then it would be much more advantageous to acquire only specific headers for specific requests.
        headers = ctx.headerMap();
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