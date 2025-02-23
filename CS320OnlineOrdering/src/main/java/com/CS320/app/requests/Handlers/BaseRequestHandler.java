package com.CS320.app.requests.Handlers;

import java.lang.reflect.Type;

import com.CS320.app.Exceptions.SchemaException;
import com.CS320.app.requests.Request;
import com.google.gson.Gson;

import io.javalin.http.Context;

public class BaseRequestHandler extends RequestHandler {


    public BaseRequestHandler(Type classType, Context ctx) {
        super(classType, ctx);
    }

    @Override
    public void validateAgainstSchema() throws SchemaException {
        // TODO add a schema validation tool that can be grabbed at runtikme
        throw new UnsupportedOperationException("Unimplemented method 'validateAgainstSchema'");
    }

    @Override
    protected Request buildRequest() {
        Request req = new Gson().fromJson(ctx.body(), classType);
        req.setIP(ctx.ip());
        return req;
    }

    @Override
    protected void setHeaders() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setHeaders'");
    }
}
