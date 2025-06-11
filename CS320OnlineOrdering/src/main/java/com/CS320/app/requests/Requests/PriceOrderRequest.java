package com.CS320.app.requests.Requests;

import com.CS320.app.requests.Responses.PriceOrderResponse;
import com.CS320.app.requests.Responses.Response;

import io.javalin.http.Context;

public class PriceOrderRequest extends AuthenticationRequest {
    private int orderNumber;
    @Override
    public Response buildResponse(Context ctx) throws Exception {
        checkAuthenticationStatus();
        //only admin should be able to do this, TODO
        if (isAuthenticated && pkg.getSessionManager().get(token).getAdminStatus()) {
            String email = associatedSession.getEmail();
            boolean successfullyQueuedOrder = pkg.getOrdersList().queueOrder(email, orderNumber);
            return new PriceOrderResponse(successfullyQueuedOrder, requestType, token);
        }
        else {
            //I don't tell the user why their request was denied, they aren't authenticated here
            return new PriceOrderResponse(false, requestType, token);
        }
    }
    
}
