package com.CS320.app.misc;

import java.util.Comparator;

import com.CS320.app.server.Session;

public class SessionComparator implements Comparator<Session> {

    @Override
    public int compare(Session a, Session b) {
        if (a.getPriority() < b.getPriority()) {
            return -1;
        }
        else if (a.getPriority() > b.getPriority()) {
            return 1;
        }
        return 0;
    }
    
}
