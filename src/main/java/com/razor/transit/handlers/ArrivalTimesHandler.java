package com.razor.transit.handlers;

import com.google.gson.annotations.SerializedName;
import com.razor.transit.comms.IReply;
import com.razor.transit.comms.IReplyHandler;
import com.razor.transit.comms.IRequest;
import com.razor.transit.comms.IRequestContent;
import com.razor.transit.comms.replies.ModelReply;
import com.razor.transit.models.ArrivalTimeCollectionDTO;
import com.razor.transit.models.ArrivalTimeDTO;
import com.razor.transit.viewmodels.ArrivalTimeViewModel;
import com.razor.transit.viewmodels.IViewModel;
import razor.android.transit.R;

import java.util.ArrayList;
import java.util.List;

public class ArrivalTimesHandler extends BaseHandler implements IReplyHandler
{

    public static class ArrivalTimesRequest extends IRequestContent {
        @SerializedName("stopCode")
        public String stopCode;
    }

    /**
     * Published interfaces
     * @author phemmings
     */

    public interface OnArrivalTimesRequestListener
    {
        public void onArrivalTimesLoaded(List<IViewModel> arrivalTimes);
        public void onArrivalTimesLoadFailed(String errorMessage);
    }

	/*
	 * Properties
	 */

    protected OnArrivalTimesRequestListener onArrivalTimesRequestListener;

    /**
     * Constructor
     */

    public ArrivalTimesHandler() {
    }

    /**
     * List Events
     * @param listener
     * @param request
     */

    public void listArrivalTimes(final OnArrivalTimesRequestListener listener,
                                 final String stopCode){

        ArrivalTimesRequest arrivalTimesRequest = new ArrivalTimesRequest();
        arrivalTimesRequest.stopCode = stopCode;

        this.onArrivalTimesRequestListener = listener;

        super.getRequestHandler().handleRequest(
                R.string.request_arrival_times_list,
                arrivalTimesRequest,
                this
        );
    }

    /**
     * Handle reply
     * @param reply : IReply
     */

    public void onReply(final IRequest request,
                        final IReply reply) {

        if (reply.getMessageType() == R.string.request_arrival_times_list){
            if (reply.getResponseCode() == 0) {

                ModelReply<ArrivalTimeCollectionDTO> modelReply
                        = (ModelReply<ArrivalTimeCollectionDTO>) reply;

                ArrivalTimeCollectionDTO arrivalTimeCollectionDTO
                        = modelReply.getModel();

                List<IViewModel> list = new ArrayList<IViewModel>();
                if (arrivalTimeCollectionDTO != null
                        && arrivalTimeCollectionDTO.getTimes().size() > 0) {

                    for (ArrivalTimeDTO item: arrivalTimeCollectionDTO.getTimes()) {

                        ArrivalTimeViewModel model = new ArrivalTimeViewModel();
                        model.setModel(item);
                        list.add(model);
                    }
                }
                this.onArrivalTimesRequestListener.onArrivalTimesLoaded(list);
            } else {
                this.onArrivalTimesRequestListener.onArrivalTimesLoadFailed(reply.getErrorMessage());
            }
        }

    }

}
