package com.CS320.app.requests.Responses;

import java.util.List;
import java.util.Map;

import com.CS320.app.misc.Order;

public class GetUserOrdersResponse extends AuthenticationResponse {
    private Map<String, List<Order>> userOrdersMap;

    public GetUserOrdersResponse(String cookieValue, boolean authenticated, String responseType, Map<String, List<Order>> userOrdersMap) {
        super(cookieValue, authenticated, responseType);
        this.userOrdersMap = userOrdersMap;
    }
    
}
