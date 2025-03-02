package com.CS320.app.server;


import com.CS320.app.Exceptions.SchemaException;
import com.CS320.app.requests.Handlers.AdminRequestHandler;
import com.CS320.app.requests.Handlers.BaseRequestHandler;
import com.CS320.app.requests.Requests.CheckoutRequest;
import com.CS320.app.requests.Requests.CreateUserRequest;
import com.CS320.app.requests.Requests.GetAvailableItemsRequest;
import com.CS320.app.requests.Requests.LogInRequest;
import com.CS320.app.requests.Requests.SearchCardsRequest;
import com.CS320.app.requests.Requests.UpdateCardsRequest;
import com.CS320.app.requests.Responses.Response;

import io.javalin.Javalin;
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
            //fix
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
        app.post("/api/Admin/UpdateCardList", ctx -> {
            String response = sendToJson(webserverThreadController.baseControlFlow(new AdminRequestHandler(UpdateCardsRequest.class, ctx)));
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

}
