package com.CS320.app.requests;

public class CreateUserResponse extends Response {
    private boolean wasCreated;
    public CreateUserResponse(Boolean status) {
        wasCreated = status;
    }
}
