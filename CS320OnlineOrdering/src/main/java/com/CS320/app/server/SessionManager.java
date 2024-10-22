package com.CS320.app.server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;

import com.CS320.app.misc.SessionComparator;

public class SessionManager extends Thread{

       private static SessionManager manager = new SessionManager();
        HashMap<Session, Session> sessionLogger = new HashMap<Session, Session>();

       private SessionManager() {
       }

       public static SessionManager getInstance() {
        return manager;
       }

       public void add(Session t) {
        synchronized(manager) {
            sessionLogger.put(t, t);
        }
       }

       public boolean contains(String email, String token) {
        synchronized(sessionLogger) {
            Session dummy = new Session(email, token);
            if (sessionLogger.containsKey(dummy)) {
                sessionLogger.get(dummy).updateActivity();
                return true;
            }
            return false;
        }
       }
       @Override
       public void start() {
        while(true) {
            try {
                Thread.sleep(1800000);
                synchronized(sessionLogger) {
                    Iterator<Session> iter = sessionLogger.values().iterator();
                    while (iter.hasNext()) {
                        Session currSession = iter.next();
                        if (currSession.timeSinceActivity() > 7600000) {
                            sessionLogger.remove(currSession);
                            System.out.println(String.format("Removed session with code \"%s\"", String.valueOf(currSession.hashCode())));
                        }
                    }
                }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
       }
    }