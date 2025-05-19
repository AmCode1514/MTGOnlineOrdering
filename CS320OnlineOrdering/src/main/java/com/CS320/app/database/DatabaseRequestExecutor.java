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
    public boolean addUser(String firstname, String Lastname, String Email, String Password, int AdminStatus) {
       String query = String.format("INSERT INTO %s (FirstName, LastName, Email, Password, AdminStatus) VALUES ('%s', '%s', '%s', '%s', '%s');", DatabaseCredential.getDBName(), firstname, Lastname, Email, database.hashPassword(Password), AdminStatus); 
       return database.createUser(query); 
    }
    // this method checks if the selected email and password exist in the datbase
    public UserData signIn(String email, String enteredPassword) {
        String query = String.format("SELECT Email, Password, AdminStatus FROM %s WHERE Email = '%s';", DatabaseCredential.getDBName() ,email);
        return database.validatePassword(query, enteredPassword); 

    }
}
