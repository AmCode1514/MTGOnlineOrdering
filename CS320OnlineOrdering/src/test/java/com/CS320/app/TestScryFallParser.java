package com.CS320.app;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import com.CS320.app.CardResources.ScryFallParser;

/**
 * Unit test for simple App.
 */
public class TestScryFallParser {
    @Test
    public void testCardOutput() {
        try {
            assertEquals("Trebcg", ScryFallParser.parseFromJSONAndSort().getCards().get(0).getName());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testCardOutput2() {
        try {
            assertEquals("Trebcg", ScryFallParser.parseFromJSONAndSort().getCards().get(0).getPrices().getusd());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
