package com.CS320.app.requests;

import com.CS320.app.misc.ServerResourcePackage;

public class UpdateCardsRequest extends AuthenticationRequest {
    private String token; 
    private transient ServerResourcePackage pkg;
    public Response buildResponse() {
        try{
            long initialTime = System.currentTimeMillis();
            pkg.getController().haltServerRequestsAndUpdateCardList();
            int timeToCompletionSeconds = (int) ((System.currentTimeMillis() - initialTime) / 1000);
            return new UpdateCardsRequestResponse(true, timeToCompletionSeconds);
        }
        catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
