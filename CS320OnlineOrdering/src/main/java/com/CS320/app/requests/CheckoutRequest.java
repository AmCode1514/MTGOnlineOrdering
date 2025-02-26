package com.CS320.app.requests;

import java.util.ArrayList;

import com.CS320.app.misc.Order;
import com.CS320.app.misc.ServerResourcePackage;
import com.CS320.app.server.SessionManager;

public class CheckoutRequest extends AuthenticationRequest {
    private String[] itemNames;
    private String status;

    @Override
    public Response buildResponse() {
        if (token == null) {
            status = "RedirectLogIn";
            return new CheckoutResponse(status, isAuthenticated, 0.0);
        }
        checkAuthenticationStatus();
            if(isAuthenticated) {
                //this is a placeholder, this code should then create an order with an associated session that can be accessed from the administrator panel, additionally, the associated user should be able to
                //fetch their own order. 
                return new CheckoutResponse(status, isAuthenticated, 0);
            }
            else {
                status = "TokenInvalid";
                return new CheckoutResponse(status, isAuthenticated, 0.0);
            }
        }

    

}
