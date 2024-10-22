package com.CS320.app.requests;

public class LogInResponse extends AuthenticationResponse {
    public LogInResponse(String cookieValue, boolean authenticated) {
        super.cookieValue = cookieValue;
        super.authenticated = authenticated;
    }
}
