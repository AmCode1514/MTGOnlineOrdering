package com.CS320.app.requests.Responses;

public class AuthenticationResponse extends Response {
    protected transient String cookieValue;
    protected boolean authenticated;
    public String getCookie() {
        return this.cookieValue;
    }
}
