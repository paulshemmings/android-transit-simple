package com.razor.transit.comms.providers;

import com.razor.transit.comms.IHandler;
import com.razor.transit.comms.IProvider;
import com.razor.transit.comms.IReply;
import com.razor.transit.comms.IReplyBuilder;
import com.razor.transit.comms.IReplyHandler;
import com.razor.transit.comms.IRequest;
import com.razor.transit.comms.IResponse;
import com.razor.transit.comms.replies.StatusReply;

public class DataProvider implements IProvider
{
    private IRequest request = null;
    private IResponse response = null;
    private IHandler handler = null;
    private RequestQueue requestQueue = new RequestQueue();
    private IReplyBuilder replyBuilder = null;

    public void build(final IReplyHandler replyHandler) {
        if (this.request != null
                && this.requestQueue.queueRequest(this.request, replyHandler) == 1){

            final DataProvider self = this;

            // start a thread to handle the request
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    // populate the response
                    handler.populateResponse(self.request, self.response);
                    // if we have a handler for the reply, build the reply
                    if (replyHandler != null) {
                        IReply reply = null;
                        if (self.response.getResponseCode() < 0) {
                            reply = new StatusReply();
                            reply.setMessageType(self.response.getMessageType());
                            reply.setErrorMessage(self.response.getErrorMessage());
                            reply.setResponseCode(self.response.getResponseCode());
                        } else {
                            reply = self.replyBuilder.buildReply(self.response);
                        }
                        // notify handler of reply
                        self.requestQueue.distributeReply(self.request, reply);
                    }
                }
            });
            thread.start();

        }
    }

    public IRequest getRequest()
    {
        return request;
    }

    public void setRequest(IRequest request)
    {
        this.request = request;
    }

    public IResponse getResponse()
    {
        return response;
    }

    public void setResponse(IResponse response)
    {
        this.response = response;
    }

    public IHandler getHandler()
    {
        return handler;
    }

    public void setHandler(IHandler handler)
    {
        this.handler = handler;
    }

    public IReplyBuilder getReplyBuilder()
    {
        return replyBuilder;
    }

    public void setReplyBuilder(IReplyBuilder replyBuilder)
    {
        this.replyBuilder = replyBuilder;
    }
}
