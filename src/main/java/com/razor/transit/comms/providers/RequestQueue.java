package com.razor.transit.comms.providers;

import com.razor.transit.comms.IReply;
import com.razor.transit.comms.IReplyHandler;
import com.razor.transit.comms.IRequest;
import com.razor.transit.helpers.LogHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestQueue {

    private static final String TAG = "CommsRequestQueue";
    private Map<IRequest, List<IReplyHandler>> requestQueue = null;

    /**
     * Queue the handlers that are waiting for a response for a particular request
     * @param request
     * @param handler
     * @return
     */

    public synchronized int queueRequest(final IRequest request,
                                         final IReplyHandler handler){
        int requestQueueSize = 0;
        try{
            if (requestQueue == null) {
                LogHelper.i(TAG, "build request queue");
                requestQueue = new HashMap<IRequest, List<IReplyHandler>>();
            }
            if(!requestQueue.containsKey(request)){
                LogHelper.i(TAG, "build list for request %s", request.toString());
                requestQueue.put(request, new ArrayList<IReplyHandler>());
            }
            requestQueue.get(request).add(handler);
            requestQueueSize = requestQueue.get(request).size();
        }
        catch (Exception ex) {
            LogHelper.e(TAG, "queueRequest failed with %s", ex.getMessage());
        }
        return requestQueueSize;
    }

    /**
     * Distributes the response to all the reply handlers waiting for that exact request
     * @param request
     * @param request
     * @param reply
     */

    public void distributeReply(final IRequest request,
                                final IReply reply ){
        try{
            if (requestQueue != null) {
                if (requestQueue.containsKey(request)){
                    for (IReplyHandler handler:requestQueue.get(request)){
                        if (handler != null) {
                            LogHelper.i(TAG, "distributing a reply for %s", request.toString());
                            handler.onReply(request, reply);
                        }
                    }
                    requestQueue.remove(request);
                }
            }
        }
        catch (Exception ex) {
            LogHelper.e(TAG, "queueRequest failed with %s", ex.getMessage());
        }
    }
}
