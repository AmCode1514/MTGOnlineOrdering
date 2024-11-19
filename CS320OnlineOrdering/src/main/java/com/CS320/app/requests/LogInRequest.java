package com.CS320.app.requests;

import com.CS320.app.database.DatabaseRequestExecutor;
import com.CS320.app.server.Session;
import com.CS320.app.server.SessionManager;
import com.CS320.app.server.TokenGenerator;

public class LogInRequest extends Request{
    private String token;
    private String email;
    private String password;

    public LogInRequest() {

    }

    @Override 
    public Response buildResponse() {
        DatabaseRequestExecutor exec = new DatabaseRequestExecutor();
        if (exec.signInSignal(email, password) && !SessionManager.contains(token)) {
            String sessionToken = TokenGenerator.generateRandomHexStringOfLength(32);
            SessionManager.add(sessionToken, new Session(super.ip, sessionToken, email));
            return new LogInResponse(sessionToken, true);
        }
        else if (exec.signInSignal(email, password) && SessionManager.contains(token)) {
            //remove old token, reissue.
            SessionManager.removeByEmail(email);
            String sessionToken = TokenGenerator.generateRandomHexStringOfLength(32);
            SessionManager.add(sessionToken, new Session(super.ip, sessionToken, email));
            return new LogInResponse(sessionToken, true);
        }
        return new LogInResponse("null", false);
    }
}
