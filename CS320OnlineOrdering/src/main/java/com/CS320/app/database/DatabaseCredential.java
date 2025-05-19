package com.CS320.app.database;


public class DatabaseCredential {
    //not specifying db name here, change this class in the future to reflect existing tables and db names.
    private static final String databaseName = "users";
    private static final String url = "jdbc:mariadb://localhost:3306/mtgonlineordering";
    private static final String user = "Heroes";
    private static final String password = "Bilohorivka1514!";

    public static String getUrl() {
        return url;
    }
    public static String getUser() {
        return user;
    }
    public static String getPassword() {
        return password;
    }
    public static String getDBName() {
        return databaseName;
    }
}