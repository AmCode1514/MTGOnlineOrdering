package com.CS320.app.requests.Responses;

public class AuthenticationResponse extends Response {
    protected transient String cookieValue;
    protected boolean authenticated;
    public AuthenticationResponse(String cookieValue, boolean authenticated, String responseType) {
        super(responseType);
        this.cookieValue = cookieValue;
        this.authenticated = authenticated;
    }
    public String getCookie() {
        return this.cookieValue;
    }
}
