package com.razor.transit.handlers;

import com.razor.transit.comms.IRequestHandler;


public class BaseHandler {

    IRequestHandler requestHandler = null;

    /**
     * Accessors
     */

    public IRequestHandler getRequestHandler()
    {
        return requestHandler;
    }

    public void setRequestHandler(final IRequestHandler requestHandler)
    {
        this.requestHandler = requestHandler;
    }
}
