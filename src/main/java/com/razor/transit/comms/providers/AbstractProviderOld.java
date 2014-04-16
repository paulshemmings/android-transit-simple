package com.razor.transit.comms.providers;

import com.razor.transit.comms.IHandler;
import com.razor.transit.comms.IProvider;
import com.razor.transit.comms.IReply;
import com.razor.transit.comms.IReplyHandler;
import com.razor.transit.comms.IRequest;
import com.razor.transit.comms.IRequestContent;
import com.razor.transit.comms.IResponse;

public abstract class AbstractProviderOld implements IProvider
{

    /**
     * Build the REST specific request from the generic communication request
     * @param content : IRequestContent
     * @param messageType	: int
     * @return				: IRequest
     */

    public abstract IRequest buildRequest(IRequestContent content, int messageType);

    /**
     * Build the message specific REST response object to be returned
     * @param messageType 	: int
     * @return				: IResponse
     */

    public abstract IResponse buildResponse(int messageType);

    /**
     * Build the generic ICommReply object from the specific ICommRESTResponse
     * @param response		: IResponse
     * @return				: ICommReply
     */

    public abstract IReply buildReply(IResponse response);

    /**
     * Build the message specific handler that will populate the response from the request
     * @param messageType	: int
     * @return				: IHandler
     */

    public abstract IHandler buildHandler(int messageType);

    /**
     * Queue for all requests. If any duplicate requests come in before
     * a response returns, then it is added to the distribution queue.
     */

    RequestQueue requestQueue = new RequestQueue();

    /**
     * REST implementation of the generic COMM request handle method
     * @param messageType	: int
     * @param replyHandler	: ICommReplyHandler
     * @param content		: IRequestContent
     */

    public void handleRequest(
            final int messageType,
            final IReplyHandler replyHandler,
            final IRequestContent content) {

        // build the request
        final IRequest restRequest = buildRequest(content, messageType);

        if (content != null
                && restRequest != null
                && this.requestQueue.queueRequest(restRequest, replyHandler) == 1){

            // start a thread to handle the request
            Thread thread = new Thread(new Runnable() {
                public void run() {
                    // build the response to populate
                    IResponse restResponse = buildResponse(messageType);
                    restResponse.setMessageType(messageType);
                    // build the handler to populate the response
                    IHandler handler = buildHandler(messageType);
                    // populate the response
                    // handler.populateResponse(AbstractProviderOld.this, restRequest, restResponse);
                    // if we have a handler for the reply, build the reply
                    if (replyHandler != null) {
                        // build the reply from the response
                        IReply restReply = buildReply(restResponse);
                        // notify handler of reply
                        //replyHandler.onCommsReply(request, restReply);
                        AbstractProviderOld.this.requestQueue.distributeReply(restRequest, restReply);
                    }

                }
            });
            thread.start();

        }
    }

}