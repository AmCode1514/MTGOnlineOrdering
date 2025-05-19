package com.CS320.app.requests.Requests;

import org.eclipse.jetty.server.Server;

import com.CS320.app.database.DatabaseRequestExecutor;
import com.CS320.app.database.UserData;
import com.CS320.app.misc.ServerResourcePackage;
import com.CS320.app.requests.Responses.LogInResponse;
import com.CS320.app.requests.Responses.Response;
import com.CS320.app.server.Session;
import com.CS320.app.server.SessionManager;
import com.CS320.app.server.TokenGenerator;

import io.javalin.http.Context;

public class LogInRequest extends Request {
    private String token;
    private String email;
    private String password;

    public LogInRequest() {

    }

    @Override
    public Response buildResponse(Context ctx) {
        DatabaseRequestExecutor exec = new DatabaseRequestExecutor();
        SessionManager manager = pkg.getSessionManager();
        // I need more database code, I will fetch information about admin status and
        // update accordingly.
        //Additionally, this is bad branching. Check a token 
        UserData data = exec.signIn(email, password);

        if (data != null) {
            if (data.isAuthenticated) {
                if (manager.containsByEmail(email)) {
                    Session associatedSession = manager.getByEmail(email);
                    ctx.cookie("Token", associatedSession.getToken());
                    return new LogInResponse(associatedSession.getToken(), true, requestType);
                }
                else {
                    String sessionToken = TokenGenerator.generateRandomHexStringOfLength(32);
                    manager.add(sessionToken, new Session(super.ip, sessionToken, email, data.adminStatus));
                    ctx.cookie("Token", sessionToken);
                    return new LogInResponse(sessionToken, true, requestType);
                }
            }
        }
        return new LogInResponse("null", false, requestType);
    }

}
