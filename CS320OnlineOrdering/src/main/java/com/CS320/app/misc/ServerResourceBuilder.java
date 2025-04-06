package com.CS320.app.misc;

import com.CS320.app.CardResources.CardListAccessor;
import com.CS320.app.server.Controller;
import com.CS320.app.server.OrdersList;
import com.CS320.app.server.SessionManager;

public class ServerResourceBuilder {
    SessionManager manager;
    Controller controller;
    ServerResourcePackage pkg;
    CardListAccessor accessor;
    OrdersList orders;
    public ServerResourceBuilder() {
    }

    public ServerResourceBuilder withManager(SessionManager manager) {
        this.manager = manager;
        return this;
    }
    public ServerResourceBuilder withController(Controller controller) {
        this.controller = controller;
        return this;
    }
    public ServerResourceBuilder withCardListAccessor(CardListAccessor accessor) {
        this.accessor = accessor;
        return this;
    }
    public ServerResourceBuilder withOrdersList(OrdersList orders) {
        this.orders = orders;
        return this;
    }
    public ServerResourcePackage build() {
        return new ServerResourcePackage(this);
    }
}
