package com.razor.transit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import com.razor.transit.comms.adapters.ContextResourceMapper;
import com.razor.transit.comms.providers.TransitProviderOld;
import com.razor.transit.comms.providers.TransitRequestHandler;
import com.razor.transit.handlers.StopsHandler;
import com.razor.transit.holders.RouteListViewHolder;
import com.razor.transit.holders.StopListViewHolder;
import com.razor.transit.viewmodels.IViewModel;
import com.razor.transit.viewmodels.RouteViewModel;
import com.razor.transit.viewmodels.StopViewModel;

import java.util.List;

public class StopListActivity extends       BaseActivity
                              implements    StopListViewHolder.OnStopListViewListener,
                                            StopsHandler.OnStopsLoadListener

{

    private View activityContent = null;
    private StopsHandler stopsHandler = null;
    private StopListViewHolder stopListViewHolder = null;

    public static final String STOP_LIST_AGENCY_MODEL_INTENT_VALUE =
            "StopListAgencyModelIntentValue";

    private RouteViewModel routeViewModel = null;


    @Override
    public void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        LayoutInflater li = this.getLayoutInflater();
        this.activityContent = li.inflate(StopListViewHolder.LayoutId, null);
        this.setContentView(this.activityContent);

        // build holder

        this.stopListViewHolder = new StopListViewHolder(this, this.activityContent);

        // build handler

        TransitProviderOld provider = new TransitProviderOld();
        provider.setMessageResourceMapper(new ContextResourceMapper());
        this.stopsHandler = new StopsHandler(this);
        this.stopsHandler.setRequestHandler(new TransitRequestHandler());

        // populate based on intent

        Object intentValue = this.getIntentSerializableValue(STOP_LIST_AGENCY_MODEL_INTENT_VALUE);
        if (intentValue != null) {
            this.showProgressDialog(true);
            this.routeViewModel = (RouteViewModel) intentValue;
            this.stopsHandler.requestStops(this,
                                           this.routeViewModel.getAgencyName(),
                                           this.routeViewModel.getCode());
        }
    }

    @Override
    public void onStopListItemSelected(final StopViewModel viewModel)
    {
        Intent showArrivalTimesIntent
                = new Intent(this, ArrivalTimeListActivity.class);

        showArrivalTimesIntent
                .putExtra(ArrivalTimeListActivity.ARRIVAL_TIME_LIST_STOP_MODEL_INTENT_VALUE, viewModel);

        this.startActivity(showArrivalTimesIntent);
    }

    @Override
    public void onStopsLoaded(final List<IViewModel> stops)
    {
        final StopListActivity self = this;
        this.runOnUiThread(new Runnable(){
            public void run() {
                self.showProgressDialog(false);
                self.stopListViewHolder.updateStops(stops);
            }
        });
    }

    @Override
    public void onStopsLoadFailed(final String errorMessage)
    {
        // todo: show error
    }
}