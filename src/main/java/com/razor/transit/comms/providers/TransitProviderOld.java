package com.razor.transit.comms.providers;

import com.razor.transit.comms.IHandler;
import com.razor.transit.comms.IMessageResourceMapper;
import com.razor.transit.comms.IProvider;
import com.razor.transit.comms.IReply;
import com.razor.transit.comms.IRequest;
import com.razor.transit.comms.IRequestContent;
import com.razor.transit.comms.IResponse;
import com.razor.transit.applications.TransitApplication;
import com.razor.transit.comms.handlers.SimpleJsonHandler;
import com.razor.transit.comms.replies.ModelReply;
import com.razor.transit.comms.replies.StatusReply;
import com.razor.transit.comms.requests.SimpleJsonRequest;
import com.razor.transit.comms.responses.AgencyCollectionResponse;
import com.razor.transit.comms.responses.SimpleJsonResponse;
import com.razor.transit.helpers.JsonHelper;
import com.razor.transit.models.AgencyCollectionDTO;
import com.razor.transit.models.RouteCollectionDTO;
import razor.android.transit.R;

public class TransitProviderOld {

    private String sessionId;
    private IMessageResourceMapper messageResourceMapper;

    public void setSessionId(final String sessionId){ this.sessionId = sessionId;}
    public String getSessionId(){ return sessionId;}

    public IMessageResourceMapper getMessageResourceMapper()
    {
        return messageResourceMapper;
    }
    public void setMessageResourceMapper(final IMessageResourceMapper messageResourceMapper)
    {
        this.messageResourceMapper = messageResourceMapper;
    }

    /**
     * Build the rest request based on the IRequest object
     */

    public IRequest buildRequest(final IRequestContent content, final int messageType){
        String messageUrl = this.messageResourceMapper.getMessageUrl(messageType);
        IRequest restRequest = new SimpleJsonRequest(content, messageUrl);
        return restRequest;
    }

    /**
     * Return an instance of the IResponse that is to be filled in
     */

    public IResponse buildResponse(final int messageType) {
        IResponse restResponse = new SimpleJsonResponse();
        return restResponse;
    }

    /**
     * Return instance of REST handler to use to populate IResponse
     */

    public IHandler buildHandler(int messageType) {
        IHandler restHandler = new SimpleJsonHandler();
        return restHandler;
    }

    /**
     * Build the REST reply from the IResponse object
     */

    public IReply buildReply(final IResponse restResponse) {

        IReply builtReply = null;

        if (restResponse.getResponseCode() < 0) {
            StatusReply statusReply = new StatusReply();
            statusReply.setErrorMessage(restResponse.getErrorMessage());
            statusReply.setResponseCode(restResponse.getResponseCode());
            return statusReply;
        }

        if (restResponse.getMessageType() == R.string.request_agency_list){

            JsonHelper<AgencyCollectionDTO> helper = new JsonHelper<AgencyCollectionDTO>(AgencyCollectionDTO.class);
            AgencyCollectionDTO agencyCollectionDTO = helper.fromJson((String)
                    restResponse.getResponseContent());

            ModelReply<AgencyCollectionDTO> modelReply = new ModelReply<AgencyCollectionDTO>(restResponse.getMessageType());
            modelReply.addItem(agencyCollectionDTO);
            modelReply.setResponseCode(0);
            modelReply.setErrorMessage("");

            return modelReply;
        }

        if (restResponse.getMessageType() == R.string.request_route_list) {

            JsonHelper<RouteCollectionDTO> helper = new JsonHelper<RouteCollectionDTO>(RouteCollectionDTO.class);

            RouteCollectionDTO routeCollectionDTO = helper.fromJson((String)
                    restResponse.getResponseContent());

            ModelReply<RouteCollectionDTO> modelReply = new ModelReply<RouteCollectionDTO>(restResponse.getMessageType());
            modelReply.addItem(routeCollectionDTO);
            modelReply.setResponseCode(0);
            modelReply.setErrorMessage("");

            return modelReply;
        }

        return builtReply;
    }

    public boolean isFacingProduction() {
        return false;
    }

    public String getUrl() {
        return this.messageResourceMapper.getRootUrl();
    }
}