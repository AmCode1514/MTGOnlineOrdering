package com.CS320.app.server;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


import com.CS320.app.CardResources.CardListAccessor;
import com.CS320.app.requests.Request;
import com.CS320.app.requests.Response;

import io.javalin.Javalin;

public class Controller {

    private AtomicInteger numberOfActiveThreads;
    private final Javalin runningWebserver;
    private final SessionManager sessionTracker;
    private volatile boolean haltThreads;
    private static final Logger logger = Logger.getLogger("Master");

    public Controller(Javalin app, SessionManager manager) throws Exception {
        try {
            FileHandler handler = new FileHandler();
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
        } catch (IOException e) {
            throw e;
        }
        numberOfActiveThreads = new AtomicInteger();
        this.runningWebserver = app;
        this.sessionTracker = manager;
        haltThreads = false;
    }

    // don't have a good name for this method since its scope isn't well defined;
    /*
     * 
     * 
     * 
     *
     *
     * 
     */
    public Response controlFlow(Request req) {
        while (haltThreads) {
            synchronized(this) {
                try {
                 wait();   
                }
                catch(InterruptedException e) {
                    logger.log(Level.SEVERE, "This operation should never be interrupted", e);
                }
                
            }
        }
        numberOfActiveThreads.incrementAndGet();
        Response builtResponse = req.buildResponse();
        numberOfActiveThreads.decrementAndGet();
        return builtResponse;
    }

    private void haltServerRequestsAndUpdateCardList() {
        haltThreads = true;
        while (numberOfActiveThreads.get() != 0) {
            Thread.onSpinWait();
        }
        try {
            CardListAccessor.parseAndUpdateCardList();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage() + '\n' + e.getStackTrace());
        }
        synchronized(this) {
            haltThreads = false;
            notifyAll();
        }
    }

    private void haltServerRequests() {

    }

    private void deathenticateUser() {

    }

    private void restartServer(int port) {
    }
}
