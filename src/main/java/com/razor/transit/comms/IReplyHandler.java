package com.razor.transit.comms;

public interface IReplyHandler {
    void onReply(IRequest request, IReply reply);
}
