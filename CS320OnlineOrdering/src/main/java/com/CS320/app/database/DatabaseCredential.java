package com.CS320.app.database;


public class DatabaseCredential {

    private static final String url = "jdbc:mariadb://localhost:3306/OnlineStripperOrdering";
    private static final String user = "brent";
    private static final String password = "Coffee123";

    public static String getUrl() {
        return url;
    }
    public static String getUser() {
        return user;
    }
    public static String getPassword() {
        return password;
    }
}