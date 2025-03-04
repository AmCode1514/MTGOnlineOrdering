package com.CS320.app;

import static org.junit.jupiter.api.Assertions.*;

import java.security.SecureRandom;

import org.junit.jupiter.api.Test;
import com.CS320.app.CardResources.ScryFallParser;

/**
 * Unit test for simple App.
 */
public class TestScryFallParser {
    @Test
    public void testCardOutput() {
        try {
            byte[] by = new byte[64];
            new SecureRandom().nextBytes(by);
            assertEquals("Trebcg", new ScryFallParser().parseFromJSONAndSort(by).getCards().get(0).getName());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testCardOutput2() {
        try {
            byte[] by = new byte[64];
            new SecureRandom().nextBytes(by);
            assertEquals("Trebcg", new ScryFallParser().parseFromJSONAndSort(by).getCards().get(0).getPrices().getusd());
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
