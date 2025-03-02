package com.CS320.app.requests.Responses;

public class CheckoutResponse extends AuthenticationResponse {
    private String status;
    private double chargedAmount;
    public CheckoutResponse(String status, boolean authenticated, double total) {
        this.status = status;
        super.authenticated = authenticated;
        chargedAmount = total;

    }
}
