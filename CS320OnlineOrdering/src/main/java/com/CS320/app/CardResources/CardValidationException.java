package com.CS320.app.CardResources;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CardValidationException extends Exception {
    private String ip;
    private String body;
    private String email;
    private String addendum; 
    public CardValidationException(String otherMessage) {
        addendum = otherMessage;
    }
    //TODO store request body and flag session as suspicious
    private void logPotentialSecurityViolation() {
        if (ip != null) {
            Logger.getLogger("Master").log(Level.SEVERE, ip + " with email: " + email + " and body \n" + body + "\n" + " has potentially crafted a malicious api request");
        }
        else {
            Logger.getLogger("Master").log(Level.SEVERE, addendum);
        }
    }
    public void addUserIpEmailAndMessageBody(String ip, String body, String email) {
        this.ip = ip;
        this.body = body; 
        this.email = email;
    }
}
