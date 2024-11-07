package com.CS320.app.requests;

public class CheckoutResponse extends AuthenticationResponse {
    private boolean authenticated;
    private String status;
    public CheckoutResponse(String status, boolean authenticated) {
        this.status = status;
        this.authenticated = authenticated;
    }
}
