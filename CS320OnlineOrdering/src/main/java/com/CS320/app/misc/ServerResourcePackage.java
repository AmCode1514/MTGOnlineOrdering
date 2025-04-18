package com.CS320.app.misc;

import com.CS320.app.CardResources.CardListAccessor;
import com.CS320.app.server.Controller;
import com.CS320.app.server.OrdersList;
import com.CS320.app.server.SessionManager;

public class ServerResourcePackage {
    private final SessionManager manager;
    private final Controller controller;
    private final CardListAccessor accessor;
    private final OrdersList orders;

    public ServerResourcePackage(ServerResourceBuilder builder) {
        manager = builder.manager;
        controller = builder.controller;
        accessor = builder.accessor;
        orders = builder.orders;
    }
    public SessionManager getSessionManager() {
        return manager;
    }
    public Controller getController() {
        return controller;
    }
    public CardListAccessor getCardListAccessor() {
        return accessor;
    }
    public OrdersList getOrdersList() {
        return orders;
    }
}
