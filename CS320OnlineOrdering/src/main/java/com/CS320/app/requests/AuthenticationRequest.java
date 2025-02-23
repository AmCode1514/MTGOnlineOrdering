package com.CS320.app.requests;
import com.CS320.app.server.Session;
import com.CS320.app.server.SessionManager;
public abstract class AuthenticationRequest extends Request {
    protected String token;
    protected boolean isAuthenticated = false;
    protected Session associatedSession;
    protected void setAuthenticationStatus() {
        if (SessionManager.contains(token)) {
            isAuthenticated = true;
            associatedSession = SessionManager.get(token);
        }
    }
}
