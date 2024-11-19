package com.CS320.app.server;

import java.util.HashMap;
import java.util.Iterator;

public class SessionManager extends Thread{

       private static SessionManager manager = new SessionManager();
       private static HashMap<String, Session> sessionLogger = new HashMap<String, Session>();

       private SessionManager() {
       }

       public static SessionManager getInstance() {
        return manager;
       }

       public static void add(String token, Session t) {
        synchronized(manager) {
            sessionLogger.put(token, t);
        }
       }

       public static Session get(String token) {
        synchronized(manager) {
            return sessionLogger.get(token);
        }
       }

       public static boolean contains(String token) {
        //this isn't secure, would have to replace with guava sha 256 or another sha library/implementation
        synchronized(sessionLogger) {
            if (sessionLogger.containsKey(token)) {
                sessionLogger.get(token).updateActivity();
                return true;
            }
            return false;
        }
       }
       public static boolean removeByEmail(String email) {
        synchronized(sessionLogger) {
        for (String t : sessionLogger.keySet()) {
            if (sessionLogger.get(t).getEmail().equals(email)) {
                sessionLogger.remove(t);
                return true;
            }
        }
    }
        return false;
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