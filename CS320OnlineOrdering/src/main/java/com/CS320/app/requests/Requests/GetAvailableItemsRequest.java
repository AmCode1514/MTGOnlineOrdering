package com.CS320.app.requests.Requests;


import com.CS320.app.misc.ServerResourcePackage;
import com.CS320.app.requests.Responses.Response;

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
        return null;
    }
    @Override
    public void injectResourcePackage(ServerResourcePackage pkg) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'injectResourcePackage'");
    }
}
