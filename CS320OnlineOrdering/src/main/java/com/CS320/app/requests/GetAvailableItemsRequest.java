package com.CS320.app.requests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import com.CS320.app.misc.RequestResources.ResourceLoader;

public class GetAvailableItemsRequest extends Request {
    public int numRequestedItems;
    public GetAvailableItemsRequest() {

    }
    @Override
    public void setIP(String ip) {
        super.ip = ip;
    }

    @Override
    public Response buildResponse() {
        return new GetAvailableItemsResponse(ResourceLoader.getItems());
    }
}
