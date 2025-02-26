package com.CS320.app.server;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager extends Thread{

       private ConcurrentHashMap<String, Session> sessionLogger = new ConcurrentHashMap<String, Session>();

       //could add config options to the constructor... :)
       public SessionManager() {
       }

       public void add(String token, Session t) {
            sessionLogger.put(token, t);
       }

       public Session get(String token) {
            return sessionLogger.get(token);
       }

       public boolean contains(String token) {
        //this isn't secure, would have to replace with guava sha 256 or another sha library/implementation
            if (sessionLogger.containsKey(token)) {
                sessionLogger.get(token).updateActivity();
                return true;
            }
            return false;
       }
       public boolean removeByEmail(String email) {
        for (String t : sessionLogger.keySet()) {
            if (sessionLogger.get(t).getEmail().equals(email)) {
                sessionLogger.remove(t);
                return true;
            }
        }
        return false;
    }

       @Override
       public void start() {
        while(true) {
            try {
                Thread.sleep(1800000);
                    Iterator<Session> iter = sessionLogger.values().iterator();
                    while (iter.hasNext()) {
                        Session currSession = iter.next();
                        if (currSession.timeSinceActivity() > 7600000) {
                            sessionLogger.remove(currSession);
                            System.out.println(String.format("Removed session with code \"%s\"", String.valueOf(currSession.hashCode())));
                        }
                    }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
       }
    }