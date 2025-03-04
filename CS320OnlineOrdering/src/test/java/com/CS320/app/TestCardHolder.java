package com.CS320.app;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.security.SecureRandom;

import org.junit.jupiter.api.Test;

import com.CS320.app.CardResources.Card;
import com.CS320.app.CardResources.CardHolder;
import com.CS320.app.CardResources.ScryFallParser;


public class TestCardHolder {

    @Test
    public void basicSearchTest() {
        try {
            byte[] by = new byte[64];
            new SecureRandom().nextBytes(by);
            CardHolder test = new ScryFallParser().parseFromJSONAndSort(by);
            assertEquals(test.find("xa")[0].getName(), "xavier sal, infested captain");
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}
