package com.CS320.app.server;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


import com.CS320.app.CardResources.CardListAccessor;
import com.CS320.app.misc.ServerResourceBuilder;
import com.CS320.app.misc.ServerResourcePackage;
import com.CS320.app.requests.Request;
import com.CS320.app.requests.Response;
import com.CS320.app.requests.Handlers.RequestHandler;

import io.javalin.Javalin;

public class Controller {

    private AtomicInteger numberOfActiveThreads;
    private final Javalin runningWebserver;
    private final SessionManager sessionTracker;
    private volatile boolean haltThreads;
    private static final Logger logger = Logger.getLogger("Master");
    private final CardListAccessor cards;

    public Controller(Javalin app, SessionManager manager) throws Exception {
        try {
            FileHandler handler = new FileHandler();
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
            cards = new CardListAccessor();
        } catch (IOException e) {
            throw e;
        }
        numberOfActiveThreads = new AtomicInteger();
        this.runningWebserver = app;
        this.sessionTracker = manager;
        haltThreads = false;
        Thread t = sessionTracker;
        t.start();
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
    public Response baseControlFlow(RequestHandler handler) {
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
        //TODO, this needs refactored to use a try catch that will write errors with the logger. Additionally, that operation should be multithreaded to allow a faster response and prevent slowdown.
        numberOfActiveThreads.incrementAndGet();
        Response builtResponse = handler.injectRequiredResources(buildResourcePackage(handler)).getResponse();
        /* header functionality to be implemented in the future */
        //handler.setHeaders();
        numberOfActiveThreads.decrementAndGet();
        return builtResponse;
    }

    //TODO, halt all tasks, prevent multiple administrator requests that encounter a readers writers issue from executing concurrently, otherwise allow multiple non-conflicting executing threads.
    public Response administratorControlFlow(RequestHandler handler) {
        Response builtResponse = handler.injectRequiredResources(buildResourcePackage(handler)).getResponse();
        handler.setHeaders();
        return builtResponse;
    }

    public synchronized void haltServerRequestsAndUpdateCardList() {
        haltThreads = true;
        while (numberOfActiveThreads.get() != 0) {
            Thread.onSpinWait();
        }
        try {
            cards.parseAndUpdateCardList();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage() + '\n' + e.getStackTrace());
        }
        haltThreads = false;
        notifyAll();
    }

    private ServerResourcePackage buildResourcePackage(RequestHandler handler) {
        byte flags = handler.getRequiredResources();
        ServerResourceBuilder builder = new ServerResourceBuilder();
        if ((flags & 0b00000001) > 0) {
            builder.withManager(sessionTracker);
        }
        if ((flags & 0b00000010) > 0) {
            builder.withController(this);
        }
        if((flags & 0b00000100) > 0) {
            builder.withCardListAccessor(cards);
        }
        return builder.build();
    }

    private void haltServerRequests() {

    }

    private void deathenticateUser() {

    }

    private void restartServer(int port) {
    }
}
