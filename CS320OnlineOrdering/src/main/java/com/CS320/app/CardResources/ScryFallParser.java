package com.CS320.app.CardResources;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;



public class ScryFallParser {
    // Params None
    // Reads the downloaded scryfall json from a filename, parses it into a card object for each entry in the list, then sorts it alphabetically for use in binary search. 
    // returns list of read cards.

    public ScryFallParser() {

    }
    public CardHolder parseFromJSONAndSort(byte[] secretKey) throws IOException {
        try {
            //update the magic string to fetch env data
            //String jsonString = new String(Files.readAllBytes(Paths.get("BulkData")), StandardCharsets.UTF_8);
            ObjectMapper map = new ObjectMapper();
            map.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            CardHolder cardList =  map.readValue(Files.readAllBytes(Paths.get("BulkData.json")), CardHolder.class);
            cardList.init(secretKey);
            return cardList; 
        }
        catch(IOException e) {
            throw e;
        }
    }
    //todo
    public CardHolder downloadAndReparseCardHolder() {
        return null;
    }
}
