package com.CS320.app.requests.Requests;

import com.CS320.app.misc.ServerResourcePackage;
import com.CS320.app.requests.Responses.Response;
import com.CS320.app.requests.Responses.UpdateCardsRequestResponse;
import com.CS320.app.server.Session;

public class UpdateCardsRequest extends AuthenticationRequest { 
    public Response buildResponse() {
        try{
            long initialTime = System.currentTimeMillis();
            Session userSession = pkg.getSessionManager().get(token);
            checkAuthenticationStatus();
            //admin status will be acquired at session creation via adjustments to the db.
            if (isAuthenticated && userSession.getAdminStatus()) {
                pkg.getController().haltServerRequestsAndUpdateCardList();
                int timeToCompletionSeconds = (int) ((System.currentTimeMillis() - initialTime) / 1000);
                return new UpdateCardsRequestResponse(true, timeToCompletionSeconds, isAuthenticated ,requestType, token);
            }
            //I'm hardcoding a zero here since it isn't important if the user hasn't been authenticated. Furthermore, I don't expose any information around timing.
            return new UpdateCardsRequestResponse(false, 0, isAuthenticated ,requestType, token);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
