package com.razor.transit.comms;

import com.razor.transit.comms.providers.RequestQueue;

public interface IProvider {

    public void build(final IReplyHandler replyHandler);
    public void setRequest(IRequest request);
    public void setResponse(IResponse response);
    public void setHandler(IHandler handler);
    public void setReplyBuilder(IReplyBuilder replyBuilder);
}
