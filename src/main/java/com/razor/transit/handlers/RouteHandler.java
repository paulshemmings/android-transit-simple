package com.razor.transit.handlers;

import android.content.Context;
import com.google.gson.annotations.SerializedName;
import com.razor.transit.comms.IReply;
import com.razor.transit.comms.IReplyHandler;
import com.razor.transit.comms.IRequest;
import com.razor.transit.comms.IRequestContent;
import com.razor.transit.comms.replies.ModelReply;
import com.razor.transit.models.RouteCollectionDTO;
import com.razor.transit.models.RouteDTO;
import com.razor.transit.viewmodels.RouteViewModel;
import com.razor.transit.viewmodels.IViewModel;
import razor.android.transit.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles retrieving routes
 */

public class RouteHandler extends BaseHandler implements IReplyHandler
{

    public static class RouteRequestContent extends IRequestContent implements Serializable {
        @SerializedName("agencyName")
        public String agencyName;
    }

    /**
     * notification of routes retrieval
     * @author phemmings
     */

    public interface OnRoutesLoadListener
    {
        public void onRoutesLoaded(List<IViewModel> routes);
        public void onRoutesLoadFailed(String errorMessage);
    }

	/*
	 * Properties
	 */

    protected OnRoutesLoadListener routesLoadListener;

    /**
     * Constructor
     * @param parentContext
     */

    public RouteHandler(final Context parentContext) {
        // super(parentContext);
    }

    /**
     * List Events
     * @param listener
     * @param request
     */

    public void requestRoutes(final OnRoutesLoadListener listener, final String agencyName){

        this.routesLoadListener = listener;

        RouteRequestContent requestContent = new RouteRequestContent();
        requestContent.agencyName = agencyName;

        super.getRequestHandler().handleRequest(
                R.string.request_route_list,
                requestContent,
                this);
    }

    /**
     * Handle reply
     * @param reply : IReply
     */

    public void onReply(final IRequest request,
                        final IReply reply) {

        if (reply.getMessageType() == R.string.request_route_list){
            if (reply.getResponseCode() == 0) {
                ModelReply<RouteCollectionDTO> modelReply = (ModelReply<RouteCollectionDTO>) reply;
                RouteCollectionDTO eventCollection = modelReply.getModel();

                List<IViewModel> list = new ArrayList<IViewModel>();
                if (eventCollection != null && eventCollection.getRoutes().size() >0) {
                    for (RouteDTO item: eventCollection.getRoutes()){
                        RouteViewModel model = new RouteViewModel();
                        model.setModel(item);
                        model.setAgencyName(eventCollection.getAgencyName());
                        list.add(model);
                    }
                }
                this.routesLoadListener.onRoutesLoaded(list);
            } else {
                this.routesLoadListener.onRoutesLoadFailed(reply.getErrorMessage());
            }
        }

    }

}
