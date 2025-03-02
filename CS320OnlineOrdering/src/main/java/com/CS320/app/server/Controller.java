package com.CS320.app.server;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


import com.CS320.app.CardResources.CardListAccessor;
import com.CS320.app.misc.ServerResourceBuilder;
import com.CS320.app.misc.ServerResourcePackage;
import com.CS320.app.requests.Handlers.RequestHandler;
import com.CS320.app.requests.Responses.Response;

import io.javalin.Javalin;

public class Controller {

    private final Javalin runningWebserver;
    private final SessionManager sessionTracker;
    private static final Logger logger = Logger.getLogger("Master");
    private final CardListAccessor cards;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final ReadLock readLock = lock.readLock();
    private final WriteLock writerLock = lock.writeLock();

    public Controller(Javalin app, SessionManager manager) throws Exception {
        try {
            FileHandler handler = new FileHandler();
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
            cards = new CardListAccessor();
        } catch (IOException e) {
            throw e;
        }
        this.runningWebserver = app;
        this.sessionTracker = manager;
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
        readLock.lock();
        try {
            Response builtResponse = handler.injectRequiredResources(buildResourcePackage(handler)).getResponse();
            /* header functionality to be implemented in the future */
            //handler.setHeaders();
            return builtResponse;
        }
        catch(Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
        finally{
            readLock.unlock();
        }
    }

    public Response administratorControlFlow(RequestHandler handler) {
        writerLock.lock();
        try {
            Response builtResponse = handler.injectRequiredResources(buildResourcePackage(handler)).getResponse();
            return builtResponse;
        }
        catch(Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
            return null;
        }
        finally{
            writerLock.unlock();
        }
    }

    public synchronized void haltServerRequestsAndUpdateCardList() {
        try {
            cards.parseAndUpdateCardList();
        } catch (IOException e) {
            logger.log(Level.SEVERE, e.getMessage() + '\n' + e.getStackTrace());
        }
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
