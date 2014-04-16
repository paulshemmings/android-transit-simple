package com.razor.transit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import com.razor.transit.comms.adapters.ContextResourceMapper;
import com.razor.transit.comms.providers.TransitProviderOld;
import com.razor.transit.comms.providers.TransitRequestHandler;
import com.razor.transit.handlers.RouteHandler;
import com.razor.transit.holders.RouteListViewHolder;
import com.razor.transit.viewmodels.AgencyViewModel;
import com.razor.transit.viewmodels.IViewModel;
import com.razor.transit.viewmodels.RouteViewModel;

import java.util.List;

public class RouteListActivity extends BaseActivity implements
                                                RouteListViewHolder.OnRouteListViewRouteListener,
                                                RouteHandler.OnRoutesLoadListener

{

    private View activityContent = null;
    private RouteHandler routeHandler = null;
    private RouteListViewHolder routeListViewHolder = null;

    public static final String ROUTE_LIST_AGENCY_MODEL_INTENT_VALUE =
            "RouteListAgencyModelIntentValue";

    private AgencyViewModel agencyViewModel = null;


    @Override
    public void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        LayoutInflater li = this.getLayoutInflater();
        this.activityContent = li.inflate(RouteListViewHolder.LayoutId, null);
        this.setContentView(this.activityContent);

        // build holder

        this.routeListViewHolder = new RouteListViewHolder(this, this.activityContent);

        // build handler

        TransitProviderOld provider = new TransitProviderOld();
        provider.setMessageResourceMapper(new ContextResourceMapper());
        this.routeHandler = new RouteHandler(this);
        this.routeHandler.setRequestHandler(new TransitRequestHandler());

        // populate based on intent

        Object intentValue = this.getIntentSerializableValue(ROUTE_LIST_AGENCY_MODEL_INTENT_VALUE);
        if (intentValue != null) {
            this.showProgressDialog(true);
            this.agencyViewModel = (AgencyViewModel) intentValue;
            this.routeHandler.requestRoutes(this, this.agencyViewModel.getCode());
        }
    }

    /**
     * Interface: RouteListViewHolder.OnRouteListViewRouteListener
     */

    @Override
    public void onNearbyRoutesRequested()
    {
        // todo: load routes within a particular area
    }

    @Override
    public void onNoRoutesFound()
    {
        // todo: show no routes found
    }

    @Override
    public void onRouteListItemSelected(final RouteViewModel viewModel)
    {
        Intent showStopList = new Intent(this, StopListActivity.class);
        showStopList.putExtra(StopListActivity.STOP_LIST_AGENCY_MODEL_INTENT_VALUE, viewModel);
        this.startActivity(showStopList);
    }

    @Override
    public void onRouteMapRequested(final List<IViewModel> items)
    {
        // todo: probably nothing
    }

    /**
     * Interface: RouteHandler.OnRoutesLoadListener
     */

    @Override
    public void onRoutesLoaded(final List<IViewModel> routes) {
        final RouteListActivity self = this;
        this.runOnUiThread(new Runnable(){
            public void run() {
                self.showProgressDialog(false);
                self.routeListViewHolder.updateRoutes(routes);
            }
        });
    }

    @Override
    public void onRoutesLoadFailed(final String errorMessage)
    {
        // todo: show error on loading routes
    }
}
