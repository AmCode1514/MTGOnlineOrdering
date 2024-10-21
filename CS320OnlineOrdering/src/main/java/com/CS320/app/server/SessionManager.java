package com.CS320.app.server;

import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Set;

import com.CS320.app.misc.SessionComparator;

public class SessionManager extends Thread{

       private static SessionManager manager = new SessionManager();
       PriorityQueue<Session> queue = new PriorityQueue<Session>(new SessionComparator());
       Set<Session> sessionLogger = new HashSet<Session>();

       private SessionManager() {
       }

       public static SessionManager getInstance() {
        return manager;
       }

       public void add(Session t) {
        synchronized(manager) {
            sessionLogger.add(t);
            queue.add(t);
        }
       }

       public boolean contains(String email, String token) {
        synchronized(queue) {
            if (sessionLogger.contains(new Session(email, token))) {
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
                synchronized(queue) {
                    Iterator<Session> iter = queue.iterator();
                    while (iter.hasNext()) {
                        Session currSession = iter.next();
                        if (currSession.timeSinceActivity() > 7600000) {
                            queue.remove(currSession);
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