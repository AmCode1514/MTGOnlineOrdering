package com.CS320.app.requests.Requests;
import com.CS320.app.server.Session;
import com.CS320.app.server.SessionManager;
public abstract class AuthenticationRequest extends Request {
    protected String token;

    protected boolean isAuthenticated = false;

    protected Session associatedSession;

    protected SessionManager manager;

    protected void checkAuthenticationStatus() {
        if (manager.contains(token)) {
            isAuthenticated = true;
            associatedSession = manager.get(token);
        }
    }
}
