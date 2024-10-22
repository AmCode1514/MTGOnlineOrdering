package com.CS320.app.server;

import java.util.concurrent.ConcurrentLinkedDeque;

import com.CS320.app.requests.CreateUserRequest;
import com.CS320.app.requests.Request;
import com.CS320.app.requests.Response;

import io.javalin.Javalin;
import jakarta.servlet.http.HttpServletRequest;

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
    }

    private void processRESTfullAPIRequests(Javalin app) {
        app.post("/api/LogIn", ctx -> {});
        app.post("/api/CookieAuth", ctx -> {});
        app.post("/api/CreateUser", ctx -> {
            String response = processHTTPRequest(ctx.body(), CreateUserRequest.class);
            if (response == null) {
                ctx.status(500);
                //add appropriate headers later
                ctx.result("Failed");
            }
            else {
                ctx.result(response);
            }
            }
        );
        app.get("/", ctx -> ctx.result("Hello World"));
        app.start(port);
    }

    private String processHTTPRequest(String body, Type classType) {
        try {
            JsonValidator.validate(body);
            Gson gson = new Gson();
            Request req = gson.fromJson(body, classType);
            Response res = req.buildResponse();
            return gson.toJson(res);

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
