package com.CS320.app.requests;

import com.CS320.app.database.DatabaseRequestExecutor;
import com.CS320.app.server.Session;
import com.CS320.app.server.SessionManager;
import com.CS320.app.server.TokenGenerator;

public class LogInRequest extends Request{
    private String email;
    private String password;

    public LogInRequest() {

    }

    @Override 
    public Response buildResponse() {
        DatabaseRequestExecutor exec = new DatabaseRequestExecutor();
        if (exec.signInSignal(email, password)) {
            String sessionToken = TokenGenerator.generateRandomHexStringOfLength(16);
            SessionManager.add(sessionToken, new Session(super.ip, sessionToken, email));
            return new LogInResponse(sessionToken, true);
        }
        return null;
    }
}
