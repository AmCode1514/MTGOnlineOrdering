package com.CS320.app.database;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class Database{
    private static final Database finalInstance = new Database();
     private Database() {
    }
    public UserData validatePassword(String sql, String password){
        try(
            // connect to the database and query
            Connection conn = DriverManager.getConnection(DatabaseCredential.getUrl(), DatabaseCredential.getUser(), DatabaseCredential.getPassword());
            Statement query = conn.createStatement();
            ResultSet results = query.executeQuery(sql);
            )
            {
                if (results.next()) {
                    String fetchedEmail = results.getString(1);
                    if (fetchedEmail != null) {
                        boolean authenticationStatus = hashPassword(password).equals(results.getString(2));
                        if (authenticationStatus) {
                            return new UserData(fetchedEmail, authenticationStatus, results.getBoolean(3));
                        }
                    }
                }
                return null;
            }catch (SQLException e){
                e.printStackTrace();
                return null;
            }
        }

    public boolean createUser(String sql) {
        try(
            // connect to the database and query
            Connection conn = DriverManager.getConnection(DatabaseCredential.getUrl(), DatabaseCredential.getUser(), DatabaseCredential.getPassword());
            Statement query = conn.createStatement();
            )
            {
                return query.executeUpdate(sql) >= 1 ? true : false;


            }catch (SQLException e){
                e.printStackTrace();
                return false;
            }
    }
    public String hashPassword(String password) {
        try {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder hexString = new StringBuilder(2 * encodedhash.length);
        for (int i = 0; i < encodedhash.length; i++) {
            String hex = Integer.toHexString(0xff & encodedhash[i]);
            if(hex.length() == 1) {
            hexString.append('0');
            }
            hexString.append(hex);
                }
            return hexString.toString();
            }   
        catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
    public static Database getInstance() {
        return finalInstance;
    }
}