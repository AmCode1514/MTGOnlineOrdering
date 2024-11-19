package com.CS320.app.server;

import java.util.concurrent.ConcurrentLinkedDeque;

import com.CS320.app.requests.AuthenticationResponse;
import com.CS320.app.requests.CheckoutRequest;
import com.CS320.app.requests.CreateUserRequest;
import com.CS320.app.requests.GetAvailableItemsRequest;
import com.CS320.app.requests.LogInRequest;
import com.CS320.app.requests.Request;
import com.CS320.app.requests.Response;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.io.IOException;
import java.lang.reflect.Type;
import com.google.gson.Gson;

public class WebServer {
    private int port;
    Javalin app;
    SessionManager sessionManager;

    public WebServer(int port) {
        this.port = port;
        app = Javalin.create();
        sessionManager = SessionManager.getInstance();
        processRESTfullAPIRequests(app);
        Thread sessionThread = sessionManager;
        sessionThread.start();
    }

    private void processRESTfullAPIRequests(Javalin app) {
        app.post("/api/LogIn", ctx -> {
            String response = processHTTPRequest(ctx, LogInRequest.class, true);
            if (response == null) {
                ctx.status(500);
            }
            else {
                ctx.result(response);
            }

        });
        app.post("/api/Checkout", ctx -> {
            String response = processHTTPRequest(ctx, CheckoutRequest.class, false);
            if (response == null) {
                ctx.status(500);
            }
            else {
                ctx.result(response);
            }
        });
        app.post("/api/Available", ctx -> {
            String response = processHTTPRequest(ctx, GetAvailableItemsRequest.class, false);
            if (response == null) {
                ctx.status(500);
            }
            else {
                ctx.result(response);
            }
        });
        app.post("/api/CreateUser", ctx -> {
            String response = processHTTPRequest(ctx, CreateUserRequest.class, false);
            if (response == null) {
                ctx.status(500);
            }
            else {
                ctx.result(response);
            }
            }
        );
        app.get("/", ctx -> ctx.result("Hello World"));
        app.start(port);
    }

    private String processHTTPRequest(Context ctx, Type classType, boolean isAuthentication) {
        try {
            String body = ctx.body();
            JsonValidator.validate(body);
            Gson gson = new Gson();
            Request req = gson.fromJson(body, classType);
            req.setIP(ctx.ip());
            if (isAuthentication) {
                AuthenticationResponse res = (AuthenticationResponse) req.buildResponse();
                ctx.cookie("Session", res.getCookie());
                return gson.toJson(res);
            }
            else {
            Response res = req.buildResponse();
            return gson.toJson(res);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
