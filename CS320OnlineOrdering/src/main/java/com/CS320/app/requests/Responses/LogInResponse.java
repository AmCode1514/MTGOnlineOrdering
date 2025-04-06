package com.CS320.app.requests.Responses;

public class LogInResponse extends AuthenticationResponse {
    public LogInResponse(String cookieValue, boolean authenticated, String responseType) {
        super(cookieValue, authenticated, responseType);
    }
}
