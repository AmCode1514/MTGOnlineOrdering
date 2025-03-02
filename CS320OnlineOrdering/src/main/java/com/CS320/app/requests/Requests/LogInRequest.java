package com.CS320.app.requests.Requests;

import org.eclipse.jetty.server.Server;

import com.CS320.app.database.DatabaseRequestExecutor;
import com.CS320.app.misc.ServerResourcePackage;
import com.CS320.app.requests.Responses.LogInResponse;
import com.CS320.app.requests.Responses.Response;
import com.CS320.app.server.Session;
import com.CS320.app.server.SessionManager;
import com.CS320.app.server.TokenGenerator;

public class LogInRequest extends Request{
    private String token;
    private String email;
    private String password;
    private ServerResourcePackage pkg;

    public LogInRequest() {

    }

    @Override 
    public Response buildResponse() {
        DatabaseRequestExecutor exec = new DatabaseRequestExecutor();
        SessionManager manager = pkg.getSessionManager();
        if (exec.signInSignal(email, password) && !manager.contains(token)) {
            String sessionToken = TokenGenerator.generateRandomHexStringOfLength(32);
            manager.add(sessionToken, new Session(super.ip, sessionToken, email));
            return new LogInResponse(sessionToken, true);
        }
        else if (exec.signInSignal(email, password) && manager.contains(token)) {
            //remove old token, reissue.
            manager.removeByEmail(email);
            String sessionToken = TokenGenerator.generateRandomHexStringOfLength(32);
            manager.add(sessionToken, new Session(super.ip, sessionToken, email));
            return new LogInResponse(sessionToken, true);
        }
        return new LogInResponse("null", false);
    }

}
