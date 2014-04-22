package com.razor.transit.comms.providers;


import com.razor.transit.comms.IMessageResourceMapper;
import com.razor.transit.comms.IProvider;
import com.razor.transit.comms.IReplyBuilder;
import com.razor.transit.comms.IReplyHandler;
import com.razor.transit.comms.IRequest;
import com.razor.transit.comms.IRequestContent;
import com.razor.transit.comms.IRequestHandler;
import com.razor.transit.comms.IResponse;
import com.razor.transit.comms.adapters.ContextResourceMapper;
import com.razor.transit.comms.builders.AgencyListReplyBuilder;
import com.razor.transit.comms.builders.ArrivalTimeListReplyBuilder;
import com.razor.transit.comms.builders.ProviderBuilder;
import com.razor.transit.comms.builders.RouteListReplyBuilder;
import com.razor.transit.comms.builders.StopListReplyBuilder;
import com.razor.transit.comms.handlers.SimpleJsonHandler;
import com.razor.transit.comms.requests.SimpleJsonRequest;
import com.razor.transit.comms.responses.SimpleJsonResponse;
import razor.android.transit.R;

public class TransitRequestHandler implements IRequestHandler {

    public void handleRequest(final int messageType,
                                   final IRequestContent content,
                                   final IReplyHandler replyHandler) {

        // build a transit request using the context resource mapper
        // to get the URLs for the transit service

        IMessageResourceMapper mapper = new ContextResourceMapper();
        String url = String.format("%s%s", mapper.getRootUrl(), mapper.getMessageUrl(messageType));
        IRequest request  = new SimpleJsonRequest(content, url);

        // Build the data provider

        IProvider provider = new ProviderBuilder()
                                .addHandler(new SimpleJsonHandler())
                                .addProvider(new DataProvider())
                                .addRequest(request)
                                .addReplyBuilder(this.getReplyBuilder(messageType))
                                .addResponse(this.getResponse(messageType))
                                .buildProvider();

        // Use the provider to build the response and pass to the reply handler

        provider.build(replyHandler);
    }

    private IReplyBuilder getReplyBuilder(final int messageType) {
        if (messageType == R.string.request_agency_list) {
            return new AgencyListReplyBuilder();
        }
        if (messageType == R.string.request_route_list) {
            return new RouteListReplyBuilder();
        }
        if (messageType == R.string.request_stop_list) {
            return new StopListReplyBuilder();
        }
        if (messageType == R.string.request_arrival_times_list) {
            return new ArrivalTimeListReplyBuilder();
        }
        return null;
    }

    private IResponse getResponse(final int messageType) {
        IResponse response = new SimpleJsonResponse();
        response.setMessageType(messageType);
        return response;
    }

}
