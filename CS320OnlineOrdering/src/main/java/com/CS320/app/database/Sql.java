package com.CS320.app.database;

public class Sql {
private Database database; 

    // this method is adding a new user to the database
    public boolean addUser(String firstname, String Lastname, String Email, String points, String Password ) {
       String query =  String.format("INSERT INTO Users (FirstName, LastName, Email, Points, Password) VALUES (%s, %s, %s, %s, %s)", firstname, Lastname, Email, points, Password); 
       return database.createUser(query); 
    }


    // this method to retrieve a password with a user email 
    public boolean getPasswordWithEmail(String email) {
        String query = String.format("SELECT Password FROM Users WHERE Email = %s", email);
       return database.validatePassword(email, query); 
        
    }
}

