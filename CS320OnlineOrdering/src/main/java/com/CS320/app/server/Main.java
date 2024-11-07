package com.CS320.app.server;
import com.CS320.app.database.DatabaseRequestExecutor;
import com.CS320.app.requests.*;
/**
 * Hello world!
 */
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {

        // DatabaseRequestExecutor test = new DatabaseRequestExecutor();
        // test.addUser("brent", "jones", "brent135@douchebag.com", "0", "Brent135");

        // var app = Javalin.create(/*config*/)
        //     .get("/", ctx -> ctx.result("Hello World"))
        //     .start(7070);
        WebServer server = new WebServer(7070);

        

    }
}
