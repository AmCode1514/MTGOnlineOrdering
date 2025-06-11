package com.CS320.app.requests.Requests;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.CS320.app.misc.Order;
import com.CS320.app.requests.Responses.GetUserOrdersResponse;
import com.CS320.app.requests.Responses.Response;
import com.CS320.app.server.OrdersList;
import com.CS320.app.server.SessionManager;

import io.javalin.http.Context;

public class GetUserOrdersRequest extends AuthenticationRequest {
    // this request is used to fetch a list of orders from a given email.
    private String email;
    private boolean allOrders;

    @Override
    public Response buildResponse(Context ctx) throws Exception {
        OrdersList orders = pkg.getOrdersList();
        SessionManager manager = pkg.getSessionManager();
        checkAuthenticationStatus();
        boolean adminStatus = manager.get(token).getAdminStatus();
        // first check if the session is authenticated, if so then check if they are
        // trying to grab an order that is under their email, reject if not.
        // then, we also check if the session is an admin, if so then the branch will
        // always execute regardless of whose email they are trying to access the order
        // number of.
        if (isAuthenticated && manager.get(token).getEmail().equals(email) || (isAuthenticated && adminStatus)) {
            if (allOrders && adminStatus) {
                return new GetUserOrdersResponse(requestType, isAuthenticated, ip, orders.getAllOrders());
            }
            else {
                List<Order> userOrdersList = orders.getAllOrdersFromEmail(email);
                Map<String, List<Order>> userOrdersMap = new HashMap<String, List<Order>>();
                userOrdersMap.put(email, userOrdersList);
                return new GetUserOrdersResponse(requestType, isAuthenticated, ip, userOrdersMap);
            }

        }
        return new GetUserOrdersResponse(requestType, isAuthenticated, ip, new HashMap<String, List<Order>>());
    }
}
