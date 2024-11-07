package com.CS320.app.requests;

public class GetAvailableItemsResponse extends Response {
    public String[][] itemList;
    public GetAvailableItemsResponse(String[][] itemList) {
        this.itemList = itemList;
    }
}
