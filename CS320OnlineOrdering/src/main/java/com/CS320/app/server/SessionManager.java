package com.CS320.app.server;

import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class SessionManager extends Thread{
       private ConcurrentHashMap<String, Session> sessionLogger = new ConcurrentHashMap<String, Session>();
       private ConcurrentHashMap<String, Session> sessionsByEmail = new ConcurrentHashMap<String, Session>();

       //could add config options to the constructor... :)
       public SessionManager() {
       }

       public void add(String token, Session t) {
            sessionLogger.put(token, t);
            sessionsByEmail.put(t.getEmail(), t);
       }
       //there is some redundancy here, the get methods return null if it doesn't exist. Doing null checking is expensive, so I created contains as a way to check if a token is valid.
       public Session get(String token) {
            return sessionLogger.get(token);
       }
       public Session getByEmail(String email) {
        return sessionsByEmail.get(email);
       }

       public boolean contains(String token) {
        //this isn't secure, would have to replace with guava sha 256 or another sha library/implementation
            if (sessionLogger.containsKey(token)) {
                sessionLogger.get(token).updateActivity();
                return true;
            }
            return false;
       }
       public boolean containsByEmail(String email) {
        //this isn't secure, would have to replace with guava sha 256 or another sha library/implementation
            if (sessionsByEmail.containsKey(email)) {
                sessionsByEmail.get(email).updateActivity();
                return true;
            }
            return false;
       }

       @Override
       public void run() {
        while(true) {
            try {
                Thread.sleep(1800000);
                    Iterator<Session> iter = sessionLogger.values().iterator();
                    while (iter.hasNext()) {
                        Session currSession = iter.next();
                        if (currSession.timeSinceActivity() > 7600000) {
                            sessionLogger.remove(currSession.getToken());
                            sessionsByEmail.remove(currSession.getEmail());
                            System.out.println(String.format("Removed session with code \"%s\"", String.valueOf(currSession.getToken())));
                        }
                    }
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
       }
    }