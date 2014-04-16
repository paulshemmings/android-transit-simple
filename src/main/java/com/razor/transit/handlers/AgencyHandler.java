package com.razor.transit.handlers;

import android.content.Context;

import com.razor.transit.comms.IProvider;
import com.razor.transit.comms.IReply;
import com.razor.transit.comms.IReplyHandler;
import com.razor.transit.comms.IRequest;
import com.razor.transit.comms.IRequestContent;
import com.razor.transit.comms.replies.ModelReply;
import com.razor.transit.models.AgencyCollectionDTO;
import com.razor.transit.models.AgencyDTO;
import com.razor.transit.viewmodels.AgencyViewModel;
import com.razor.transit.viewmodels.IViewModel;
import razor.android.transit.R;

import java.util.ArrayList;
import java.util.List;

public class AgencyHandler extends BaseHandler implements IReplyHandler {

    /**
	 * Published interfaces
	 * @author phemmings
	 */
	
	public interface OnAgenciesLoadedListener
    {
		public void onAgenciesLoaded(List<IViewModel> agencyItems);
		public void onAgencyLoadFailed(String errorMessage);
	}
	
	/*
	 * Properties
	 */
	
	protected OnAgenciesLoadedListener agenciesLoadedListener;
	
	/**
	 * Constructor
	 */
	
	public AgencyHandler() {
	}

	/**
	 * List Events
	 * @param listener
	 * @param request
	 */
	
	public void listAgencies(final OnAgenciesLoadedListener listener){

		this.agenciesLoadedListener = listener;

        super.getRequestHandler().handleRequest(
                R.string.request_agency_list,
                new IRequestContent() {},
                this
        );
	}
	
	/**
	 * Handle reply
     * @param reply : IReply
	 */
	
	public void onReply(final IRequest request,
                        final IReply reply) {

        if (reply.getMessageType() == R.string.request_agency_list){
            if (reply.getResponseCode() == 0) {
                ModelReply<AgencyCollectionDTO> modelReply = (ModelReply<AgencyCollectionDTO>)reply;
                AgencyCollectionDTO eventCollection = modelReply.getModel();

                List<IViewModel> list = new ArrayList<IViewModel>();
                if (eventCollection != null && eventCollection.agencies.size() >0) {
                    for (AgencyDTO item: eventCollection.agencies){
                        AgencyViewModel model = new AgencyViewModel();
                        model.setModel(item);
                        list.add(model);
                    }
                }
                this.agenciesLoadedListener.onAgenciesLoaded(list);
            } else {
                this.agenciesLoadedListener.onAgencyLoadFailed(reply.getErrorMessage());
            }
        }

    }

}
