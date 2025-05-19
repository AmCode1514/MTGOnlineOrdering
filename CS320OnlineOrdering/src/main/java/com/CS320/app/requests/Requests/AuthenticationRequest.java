package com.CS320.app.requests.Requests;
import com.CS320.app.server.Session;
import com.CS320.app.server.SessionManager;
public abstract class AuthenticationRequest extends Request {
    protected String token;

    protected boolean isAuthenticated = false;

    protected Session associatedSession;


    protected void checkAuthenticationStatus() {
        //one way to make this more secure would be to append some value based on the users identifying information to the tokens upon creation. But not send this information back to the user.
        //this way, if someone steals the token but then sends info not consistent with the original user who created it, the server can deny or flag the request.
        //using the ip would probably be the most logical solution.
        if (pkg.getSessionManager().contains(token)) {
            isAuthenticated = true;
            associatedSession = pkg.getSessionManager().get(token);
        }
    }
}
