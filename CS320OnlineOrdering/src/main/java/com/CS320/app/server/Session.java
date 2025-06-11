package com.CS320.app.server;

import com.CS320.app.misc.Order;

public class Session {
    private String ip;
    //can easily add name easily to the sessions for use with orders
    private String token;
    private String email;
    private long time;
    private boolean isAdmin;
    private Order[] orders = new Order[4];

    public Session(String ip, String token, String email, boolean isAdmin) {
        this.ip = ip;
        this.token = token;
        this.email = email;
        this.time = System.currentTimeMillis();
        this.isAdmin = isAdmin;
    }

    public long timeSinceActivity() {
        return System.currentTimeMillis() - time;
    }
    public String getEmail() {
        return email;
    }
    public String getToken() {
        return token;
    }
    public void updateActivity() {
        time = System.currentTimeMillis();
    }
    public void setOrder(Order order) throws ArrayIndexOutOfBoundsException{
        for(int i = 0; i < orders.length; ++i) {
            if (orders[i] == null) {
                orders[i] = order;
                return;
            }
        }
        throw new ArrayIndexOutOfBoundsException("Current session has maximum number of loggable orders");
    }
    public boolean getAdminStatus() {
        return isAdmin;
    }
}
