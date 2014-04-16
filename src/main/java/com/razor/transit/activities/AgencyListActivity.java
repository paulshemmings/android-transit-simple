package com.razor.transit.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.razor.transit.adapters.AgencyListAdapter;
import com.razor.transit.adapters.ViewModelListAdapter;
import com.razor.transit.adapters.ViewModelListAdapter.OnViewModelListAdapterListener;
import com.razor.transit.comms.adapters.ContextResourceMapper;
import com.razor.transit.comms.providers.TransitProviderOld;
import com.razor.transit.comms.providers.TransitRequestHandler;
import com.razor.transit.handlers.AgencyHandler;
import com.razor.transit.helpers.EndlessScrollListener.OnLoadMoreDataListener;
import com.razor.transit.holders.ViewModelListViewHolder;
import com.razor.transit.holders.ViewModelListViewHolder.ViewModelListViewAdapterBuilder;
import com.razor.transit.viewmodels.AgencyViewModel;
import com.razor.transit.viewmodels.IViewModel;
import razor.android.transit.R;

import java.util.List;

public class AgencyListActivity extends Activity implements AgencyHandler.OnAgenciesLoadedListener,
                                                            OnLoadMoreDataListener,
                                                            OnViewModelListAdapterListener
{
    private ViewModelListViewHolder viewModelListHolder = null;
    private AgencyHandler handler = null;
    private int pageNumber = 1;

    /**
     * Create the activity
     * @param savedInstanceState : Bundle
     */

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set up the list view

        this.setContentView(R.layout.lib_viewmodel_list_layout);
        this.viewModelListHolder = new ViewModelListViewHolder(this.findViewById(R.id.viewmodel_list));
        this.viewModelListHolder.setOnMoreDataListener(this);

        // build Handler

        this.handler = new AgencyHandler();
        this.handler.setRequestHandler(new TransitRequestHandler());

        // populate the list view

        this.viewModelListHolder.setBuilder( new ViewModelListViewAdapterBuilder() {
            public ViewModelListAdapter createAdapter(final Context context,
                                                      final List<IViewModel> models) {
                return new AgencyListAdapter(context, models);
            }
        });

        // get the agencies

        this.requestAgencyList();
    }

    /**
     * Interface: EndlessScrollListener.OnLoadMoreDataListener
     * Notifies when more data is requested. This is when you scroll
     * down past the bottom of the presently loaded list.
     */

    @Override
    public void onScrollRequestsMoreData(final int page) {
        // do nothing - not paginated
    }

    /**
     * Interface: AgencyHandler.OnAgenciesLoadedListener
     * Notifies when the agencies are loaded successfully / unsuccessfully.
     */

    @Override
    public void onAgenciesLoaded(final List<IViewModel> agencyItems) {
        this.runOnUiThread(new Runnable(){
            public void run() {
                // AgencyListActivity.this.showProgressDialog(false);
                AgencyListActivity.this.pageNumber = 1;
                AgencyListActivity.this.updateAgencyItems(agencyItems);
            }
        });
    }

    @Override
    public void onAgencyLoadFailed(final String errorMessage)
    {
        //todo: show no agencies available
    }

    /**
     * Interface: ViewModelListAdapter.OnViewModelListAdapterListener
     * Notifies that an item in the list view has been selected
     */

    @Override
    public void onListItemSelected(View view)
    {
        AgencyViewModel viewModel = (AgencyViewModel) view.getTag();

        Intent showRouteList = new Intent(this, RouteListActivity.class);
        showRouteList.putExtra(RouteListActivity.ROUTE_LIST_AGENCY_MODEL_INTENT_VALUE, viewModel);
        this.startActivity(showRouteList);
    }

    /**
     * Get Agencies
     * @param items
     */

    private void requestAgencyList(){
        this.handler.listAgencies(this);
    }

    /**
     * update the event items
     */

    private void updateAgencyItems(final List<IViewModel> items){
        if (items != null) {
            if (this.pageNumber == 1) {
                this.viewModelListHolder.clearAdapter();
                this.viewModelListHolder.setAdapter(items, this);
            } else {
                this.viewModelListHolder.updateAdapter(items);
            }
        } else {
            Toast.makeText(AgencyListActivity.this, "No items found", 1000).show();
        }
    }

}