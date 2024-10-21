package com.CS320.app.server;

import java.util.concurrent.ConcurrentLinkedDeque;

public class WebServer {
    private static WebServer web = new WebServer();

    private WebServer() {

    }

    public void start() {

    }
    public static WebServer getInstance() {
        return web;
    }
}
