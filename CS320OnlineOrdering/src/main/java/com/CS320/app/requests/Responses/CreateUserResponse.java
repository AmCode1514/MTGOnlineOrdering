package com.CS320.app.requests.Responses;

public class CreateUserResponse extends Response {
    private boolean wasCreated;
    public CreateUserResponse(Boolean status, String responseType) {
        super(responseType);
        wasCreated = status;
    }
}
