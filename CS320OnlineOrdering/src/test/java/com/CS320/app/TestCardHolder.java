package com.CS320.app;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import com.CS320.app.CardResources.Card;
import com.CS320.app.CardResources.CardHolder;
import com.CS320.app.CardResources.ScryFallParser;


public class TestCardHolder {

    @Test
    public void basicSearchTest() {
        try {
            CardHolder test = ScryFallParser.getScryFallParser().parseFromJSONAndSort();
            assertEquals(test.find("fa")[0].getName(), "xavier sal, infested captain");
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
