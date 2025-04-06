package com.CS320.app.requests.Responses;

public class PriceOrderResponse extends AuthenticationResponse {
    boolean successfullyQueued;
    public PriceOrderResponse(boolean successfullyQueued, String responseType, String cookieValue) {
        super(cookieValue, successfullyQueued, responseType);
        this.successfullyQueued = successfullyQueued;
    }
}
