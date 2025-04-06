package com.CS320.app.requests.Responses;

public class UpdateCardsRequestResponse extends AuthenticationResponse{
    private boolean status = false;
    private int completionTimeSeconds;
    public UpdateCardsRequestResponse(boolean status, int completionTimeSeconds, boolean authenticated ,String responseType, String cookieValue) {
        super(cookieValue, authenticated, responseType);
        this.status = status;
        this.completionTimeSeconds = completionTimeSeconds;
    }
}
