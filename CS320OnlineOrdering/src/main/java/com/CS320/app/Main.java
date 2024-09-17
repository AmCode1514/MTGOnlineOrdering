package com.CS320.app;
import com.CS320.app.requests.*;
/**
 * Hello world!
 */
import io.javalin.Javalin;

public class Main {
    public static void main(String[] args) {
        var app = Javalin.create(/*config*/)
            .get("/", ctx -> ctx.result("Hello World"))
            .start(7070);
    }
}
