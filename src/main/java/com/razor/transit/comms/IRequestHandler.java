package com.razor.transit.comms;

public interface IRequestHandler {
    void handleRequest(final int messageType,
                       final IRequestContent content,
                       final IReplyHandler replyHandler);
}
