package com.CS320.app.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet; 



public class DatabaseRequestExecutor {

    private Database database; 

    public DatabaseRequestExecutor() {
        this.database = Database.getInstance();
    }

    // this method is adding a new user to the database
    public boolean addUser(String firstname, String Lastname, String Email, String points, String Password ) {
       String query =  String.format("INSERT INTO Strippas (FirstName, LastName, Email, Points, Password) VALUES ('%s', '%s', '%s', '%s', '%s');", firstname, Lastname, Email, points, database.hashPassword(Password)); 
       return database.createUser(query); 
    }


    // this method checks if the selected email and password exist in the datbase
    public boolean signInSignal(String email, String enteredPassword) {
        String query = String.format("SELECT Password FROM Strippas WHERE Email = '%s';", email);
        return database.validatePassword(query, enteredPassword); 

    }
}
