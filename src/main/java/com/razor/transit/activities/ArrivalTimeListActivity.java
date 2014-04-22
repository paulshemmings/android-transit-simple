package com.razor.transit.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import com.razor.transit.comms.adapters.ContextResourceMapper;
import com.razor.transit.comms.providers.TransitProviderOld;
import com.razor.transit.comms.providers.TransitRequestHandler;
import com.razor.transit.handlers.ArrivalTimesHandler;
import com.razor.transit.holders.ArrivalTimeListViewHolder;
import com.razor.transit.viewmodels.IViewModel;
import com.razor.transit.viewmodels.StopViewModel;

import java.util.List;

public class ArrivalTimeListActivity 
        extends       BaseActivity
        implements    ArrivalTimeListViewHolder.OnArrivalTimesViewListener,
                      ArrivalTimesHandler.OnArrivalTimesRequestListener

{

    private View activityContent = null;
    private ArrivalTimesHandler arrivalTimesHandler = null;
    private ArrivalTimeListViewHolder arrivalTimeListViewHolder = null;

    public static final String ARRIVAL_TIME_LIST_STOP_MODEL_INTENT_VALUE =
            "ArrivalTimeStopModelIntentValue";

    private StopViewModel stopViewModel = null;


    @Override
    public void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        LayoutInflater li = this.getLayoutInflater();
        this.activityContent = li.inflate(ArrivalTimeListViewHolder.LayoutId, null);
        this.setContentView(this.activityContent);

        // build holder

        this.arrivalTimeListViewHolder
                = new ArrivalTimeListViewHolder(this, this.activityContent);

        // build handler

        TransitProviderOld provider = new TransitProviderOld();
        provider.setMessageResourceMapper(new ContextResourceMapper());

        this.arrivalTimesHandler = new ArrivalTimesHandler();
        this.arrivalTimesHandler.setRequestHandler(new TransitRequestHandler());

        // populate based on intent

        Object intentValue
                = this.getIntentSerializableValue(ARRIVAL_TIME_LIST_STOP_MODEL_INTENT_VALUE);

        if (intentValue != null) {
            this.showProgressDialog(true);
            this.stopViewModel = (StopViewModel) intentValue;
            this.arrivalTimesHandler.listArrivalTimes(
                    this,
                    this.stopViewModel.getCode()
            );
        }
    }

    @Override
    public void onArrivalTimesLoaded(final List<IViewModel> arrivalTimes)
    {
        final ArrivalTimeListActivity self = this;
        this.runOnUiThread(new Runnable(){
            public void run() {
                self.showProgressDialog(false);
                self.arrivalTimeListViewHolder.updateArrivalTimes(arrivalTimes);
            }
        });
    }

    @Override
    public void onArrivalTimesLoadFailed(final String errorMessage)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onArrivalTimesItemSelected(final StopViewModel viewModel)
    {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}