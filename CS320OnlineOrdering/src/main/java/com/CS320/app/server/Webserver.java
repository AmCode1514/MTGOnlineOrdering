package com.CS320.app.server;

import java.util.concurrent.ConcurrentLinkedDeque;

import com.CS320.app.Exceptions.SchemaException;
import com.CS320.app.requests.AuthenticationResponse;
import com.CS320.app.requests.CheckoutRequest;
import com.CS320.app.requests.CreateUserRequest;
import com.CS320.app.requests.GetAvailableItemsRequest;
import com.CS320.app.requests.LogInRequest;
import com.CS320.app.requests.Request;
import com.CS320.app.requests.Response;
import com.CS320.app.requests.SearchCardsRequest;
import com.CS320.app.requests.Handlers.BaseRequestHandler;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.io.IOException;
import java.lang.reflect.Type;
import com.google.gson.Gson;

public class WebServer {
    private final Controller webserverThreadController;
    private final int port;


    public WebServer(int port) throws Exception {
        this.port = port;
        Javalin app = Javalin.create();
        SessionManager sessionManager = new SessionManager();
        try {
            webserverThreadController = new Controller(app, sessionManager);
        }
        catch(Exception e) {
            throw e;
        }
        processRESTfullAPIRequests(app);
    }

    private void processRESTfullAPIRequests(Javalin app) {
        app.post("/api/LogIn", ctx -> {
            String response = sendToJson(webserverThreadController.baseControlFlow(new BaseRequestHandler(LogInRequest.class, ctx)));
            if (response == null) {
                ctx.status(500);
            }
            else {
                ctx.result(response);
            }
        });
        app.post("/api/Checkout", ctx -> {
            String response = sendToJson(webserverThreadController.baseControlFlow(new BaseRequestHandler(CheckoutRequest.class, ctx)));
            if (response == null) {
                ctx.status(500);
            }
            else {
                ctx.result(response);
            }
        });
        app.post("/api/Available", ctx -> {
            String response = sendToJson(webserverThreadController.baseControlFlow(new BaseRequestHandler(GetAvailableItemsRequest.class, ctx)));
            if (response == null) {
                ctx.status(500);
            }
            else {
                ctx.result(response);
            }
        });
        app.post("/api/Available/Cards", ctx -> {
            String response = sendToJson(webserverThreadController.baseControlFlow(new BaseRequestHandler(SearchCardsRequest.class, ctx)));
            if (response == null) {
                ctx.status(500);
            }
            else {
                ctx.result(response);
            }
        });
        app.post("/api/CreateUser", ctx -> {
            String response = sendToJson(webserverThreadController.baseControlFlow(new BaseRequestHandler(CreateUserRequest.class,ctx)));
            if (response == null) {
                ctx.status(500);
            }
            else {
                ctx.result(response);
            }
            }
        );
        app.get("/", ctx -> ctx.result("Hello World"));
        app.exception(SchemaException.class, (exception, ctx) -> {

        }
        );
        app.start(port);
    }


// leaving this here to decrease code repetition. Additional functionality can be added later
    private String sendToJson(Response res) {
        return new Gson().toJson(res);
    }

    // private String processHTTPRequest(Context ctx, Type classType, boolean isAuthentication) {
    //     try {
    //         String body = ctx.body();
    //         JsonValidator.validate(body);
    //         Gson gson = new Gson();
    //         Request req = gson.fromJson(body, classType);
    //         req.setIP(ctx.ip());
    //         if (isAuthentication) {
    //             Response completedResponse = webserverThreadController.controlFlow(req);
    //             AuthenticationResponse res = (AuthenticationResponse) completedResponse;
    //             if (res != null) {
    //                 ctx.cookie("Session", res.getCookie());
    //             }
    //             return gson.toJson(res);
    //         }
    //         else {
    //         Response res = webserverThreadController.controlFlow(req);
    //         return gson.toJson(res);
    //         }
    //     }
    //     catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     catch(Exception e) {
    //         e.printStackTrace();
    //     }
    //     return null;
    // }

}
