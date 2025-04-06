package com.CS320.app.requests.Responses;

public class CreateOrderResponse extends AuthenticationResponse {
    private String status;
    private double chargedAmount;
    public CreateOrderResponse(String status, boolean authenticated, double total, String cookieValue, String responseType) {
        super(cookieValue, authenticated, responseType);
        this.status = status;
        super.authenticated = authenticated;
        chargedAmount = total;

    }
}
