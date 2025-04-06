package com.CS320.app.requests.Responses;

import io.javalin.http.Context;

public abstract class Response {
    protected String responseType;
    public Response(String responseType) {
        this.responseType = responseType;
    }
}
