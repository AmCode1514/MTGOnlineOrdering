package com.CS320.app.requests.Requests;


import com.CS320.app.misc.ServerResourcePackage;
import com.CS320.app.requests.Responses.Response;

import io.javalin.http.Context;

public class GetAvailableItemsRequest extends Request {
    public int numRequestedItems;
    public GetAvailableItemsRequest() {

    }
    @Override
    public void setIP(String ip) {
        super.ip = ip;
    }

    @Override
    public Response buildResponse(Context ctx) {
        return null;
    }
    @Override
    public void injectResourcePackage(ServerResourcePackage pkg) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'injectResourcePackage'");
    }
}
