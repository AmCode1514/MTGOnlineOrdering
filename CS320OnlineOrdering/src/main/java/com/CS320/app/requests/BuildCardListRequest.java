package com.CS320.app.requests;

import com.CS320.app.misc.ServerResourcePackage;

public class BuildCardListRequest extends Request{
    ServerResourcePackage pkg;
    @Override
    public Response buildResponse() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'buildResponse'");
    }

    @Override
    public void injectResourcePackage(ServerResourcePackage pkg) {
        this.pkg = pkg;
    }
    
}
