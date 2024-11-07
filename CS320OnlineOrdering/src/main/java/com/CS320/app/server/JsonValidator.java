package com.CS320.app.server;


import java.io.IOException;
import com.google.gson.*;

import java.io.InputStream;

// org.everit.json.schema libarary to load the schema, validate the incoming JSON, 
// and check for required field and no extra properites 
import org.everit.json.schema.Schema;
import org.everit.json.schema.SchemaException;
import org.everit.json.schema.loader.SchemaLoader;
import org.everit.json.schema.ValidationException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;


public class JsonValidator {

    public static boolean validate(String json) throws IOException {
        Schema schema = getSchema("Schema class name"); 

        try {
            JSONObject jsonObject = new JSONObject(json); 

            // validate the json against the schema 
            // throw a exception if invalid 
            schema.validate(jsonObject);

            // no exception is thrown, json is valid 
            return true; 
        } catch (ValidationException e){
            //handle validateon errors 
            System.out.println("Validation error: " + e.getMessage());
            return false; 
        } catch (JSONException e) {
            System.out.println("Invalid JSON format " + e.getMessage());
            return false; 
        }
    }




    private static Schema getSchema(String className) throws IOException {
        String schemaPath = String.format("/schemas/%s.json", className); // Replace with your actual path
        try (InputStream inputStream = JsonValidator.class.getResourceAsStream(schemaPath)) {
            if (inputStream == null) {
                throw new IOException("Schema file not found: " + schemaPath);
            }

            // Load the raw schema from the JSON file
            JSONObject rawSchema = new JSONObject(new JSONTokener(inputStream));

            // Return the schema loaded from the JSON
            return SchemaLoader.load(rawSchema);
        } catch (IOException e) {
            throw new IOException("Failed to load schema: " + e.getMessage(), e);
        }
    }
    
}
