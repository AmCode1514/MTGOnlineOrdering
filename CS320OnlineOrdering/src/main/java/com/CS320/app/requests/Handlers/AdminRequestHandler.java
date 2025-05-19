package com.CS320.app.requests.Handlers;

import java.lang.reflect.Type;

import com.CS320.app.Exceptions.SchemaException;
import com.CS320.app.misc.ServerResourcePackage;
import com.google.gson.Gson;

import io.javalin.http.Context;

public class AdminRequestHandler extends RequestHandler {

     private final byte requiredResources = 0b00000111;

    public AdminRequestHandler(Type classType, Context ctx) {
        super(classType, ctx);
    }

    @Override
    public void validateAgainstSchema() throws SchemaException {
        // TODO add a schema validation tool that can be grabbed at runtikme
        throw new UnsupportedOperationException("Unimplemented method 'validateAgainstSchema'");
    }

    @Override
    protected synchronized void buildRequest() {
        if (request != null) {
            return;
        }
        request = new Gson().fromJson(ctx.body(), classType);
        request.setIP(ctx.ip());
    }

    @Override

    public void setHeaders() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setHeaders'");
    }

    @Override
    protected void buildRequestResponse() throws Exception {
        response = request.buildResponse(super.ctx);
    }
    @Override
    public byte getRequiredResources() {
        return requiredResources;
    }
}
