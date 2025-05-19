package com.CS320.app.database;

public class UserData {
    //I can add many more fields dependent on database specification. I could also introduce options for fetching based on needs.
    public final String email;
    public final boolean adminStatus;
    public final boolean isAuthenticated;
    public UserData(String email,boolean adminStatus, boolean isAuthenticated) {
        this.email = email;
        this.adminStatus = adminStatus;
        this.isAuthenticated = isAuthenticated;
    }

}
