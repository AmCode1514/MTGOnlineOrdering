package com.CS320.app.requests;

import java.util.ArrayList;

import com.CS320.app.misc.Order;
import com.CS320.app.misc.RequestResources.ResourceLoader;
import com.CS320.app.server.SessionManager;

public class CheckoutRequest extends AuthenticationRequest {
    private String token;
    private String[] itemNames;
    private boolean authenticatedWithToken;
    private String timeStamp;
    private String status;

    @Override
    public Response buildResponse() {
        if (token == null) {
            authenticatedWithToken = false;
            status = "RedirectLogIn";
            return new CheckoutResponse(status, authenticatedWithToken, 0.0);
        }
        else {
            if (SessionManager.contains(token)) {
                authenticatedWithToken = true;
                ArrayList<String> orderDetails = new ArrayList<String>();
                double total = 0.0;
                String[][] items = ResourceLoader.getItems();
                for (int i = 0; i < itemNames.length; ++i) {
                    for (int j = 0; j < items.length; ++j) {
                        if (itemNames[i].equals(items[j][0])) {
                            orderDetails.add(itemNames[0]);
                            total += Double.valueOf(items[j][1]);
                        }
                    }
                }
                //do payment processing
                SessionManager.get(token).setOrder(new Order(orderDetails, total, orderDetails.size()));
                status = "OrderPlaced";
                return new CheckoutResponse(status, authenticatedWithToken, total);
            }
            else {
                status = "TokenInvalid";
                authenticatedWithToken = false;
                return new CheckoutResponse(status, authenticatedWithToken, 0.0);
            }
        }
    }
    
}
