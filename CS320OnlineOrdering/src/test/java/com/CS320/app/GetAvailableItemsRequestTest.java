package com.CS320.app;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.CS320.app.requests.*;
/**
 * Unit test for simple App.
 */
public class GetAvailableItemsRequestTest {
    @Test
    public void testScannerOutput() {
        GetAvailableItemsRequest req = new GetAvailableItemsRequest();
        GetAvailableItemsResponse res = (GetAvailableItemsResponse) req.buildResponse();
        String[][] array = {new String[] {"Java Latte", "5.00", "A Mocha Java Latte"}, new String[] {"Drip Coffee", "3.00", "Fresh Roasted Premium Drip Coffee"}};
        assertArrayEquals(res.itemList, array);

    }
}
