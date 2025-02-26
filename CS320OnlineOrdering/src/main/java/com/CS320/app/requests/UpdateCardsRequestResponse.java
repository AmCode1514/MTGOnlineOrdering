package com.CS320.app.requests;

public class UpdateCardsRequestResponse extends Response{
    private boolean status = false;
    private int completionTimeSeconds;
    public UpdateCardsRequestResponse(boolean status, int completionTimeSeconds) {
        this.status = status;
        this.completionTimeSeconds = completionTimeSeconds;
    }
}
