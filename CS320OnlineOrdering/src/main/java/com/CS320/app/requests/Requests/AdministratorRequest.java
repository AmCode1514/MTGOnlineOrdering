package com.CS320.app.requests.Requests;

import com.CS320.app.requests.Responses.Response;
import com.CS320.app.server.Controller;

import io.javalin.http.Context;

public class AdministratorRequest extends AuthenticationRequest {
    public AdministratorRequest(Controller currentThreadController) {
        
    }

    @Override
    public Response buildResponse(Context ctx) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buildResponse'");
    }
}
