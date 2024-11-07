package com.CS320.app.server;

import com.CS320.app.misc.Order;

public class Session {
    private String ip;
    private String token;
    private String email;
    private long time;
    private Order[] orders = new Order[4];

    public Session(String ip, String token, String email) {
        this.ip = ip;
        this.token = token;
        this.email = email;
        this.time = System.currentTimeMillis();
    }

    public long timeSinceActivity() {
        return System.currentTimeMillis() - time;
    }
    public void updateActivity() {
        time = System.currentTimeMillis();
    }
    @Override
    public int hashCode() {
        return token.hashCode();
    }
    public void setOrder(Order order) throws ArrayIndexOutOfBoundsException{
        for(int i = 0; i < orders.length; ++i) {
            if (orders[i] != null) {
                orders[i] = order;
                return;
            }
        }
        throw new ArrayIndexOutOfBoundsException("Current session has maximum number of loggable orders");
    }
}
