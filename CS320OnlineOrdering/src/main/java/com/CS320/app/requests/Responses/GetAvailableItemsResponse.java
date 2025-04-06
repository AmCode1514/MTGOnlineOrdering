package com.CS320.app.requests.Responses;

public class GetAvailableItemsResponse extends Response {
    public String[][] itemList;
    public GetAvailableItemsResponse(String[][] itemList, String responseType) {
        super(responseType);
        this.itemList = itemList;
    }
}
