package com.CS320.app.requests.Requests;

import com.CS320.app.database.Database;
import com.CS320.app.database.DatabaseRequestExecutor;
import com.CS320.app.misc.ServerResourcePackage;
import com.CS320.app.requests.Responses.CreateUserResponse;
import com.CS320.app.requests.Responses.Response;

public class CreateUserRequest extends Request{
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private transient String points = "0";


    public CreateUserRequest() {
    }

    @Override
    public Response buildResponse() {
        DatabaseRequestExecutor exec = new DatabaseRequestExecutor();
        return exec.addUser(firstName, lastName, email, points, password) ? new CreateUserResponse(true) : new CreateUserResponse(false);
    }

    @Override
    public void setIP(String ip) {
        super.ip = ip;
    }
}
