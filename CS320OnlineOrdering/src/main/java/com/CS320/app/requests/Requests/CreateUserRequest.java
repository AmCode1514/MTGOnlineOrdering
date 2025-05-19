package com.CS320.app.requests.Requests;

import com.CS320.app.database.Database;
import com.CS320.app.database.DatabaseRequestExecutor;
import com.CS320.app.misc.ServerResourcePackage;
import com.CS320.app.requests.Responses.CreateUserResponse;
import com.CS320.app.requests.Responses.Response;

import io.javalin.http.Context;

public class CreateUserRequest extends Request{
    private String firstName;
    private String lastName;
    private String email;
    private String password;


    public CreateUserRequest() {
    }

    @Override
    public Response buildResponse(Context ctx) {
        DatabaseRequestExecutor exec = new DatabaseRequestExecutor();
        //default 0 for adminstatus, this ensures no created user accounts can ever be admins. Admin priviledges can only be changed directly on db
        return exec.addUser(firstName, lastName, email, password, 0) ? new CreateUserResponse(true, requestType) : new CreateUserResponse(false, requestType);
    }

    @Override
    public void setIP(String ip) {
        super.ip = ip;
    }
}
