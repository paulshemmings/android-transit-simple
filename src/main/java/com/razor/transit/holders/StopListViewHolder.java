package com.razor.transit.holders;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.razor.transit.adapters.RouteListAdapter;
import com.razor.transit.adapters.StopListAdapter;
import com.razor.transit.adapters.ViewModelListAdapter;
import com.razor.transit.helpers.EndlessScrollListener;
import com.razor.transit.viewmodels.IViewModel;
import com.razor.transit.viewmodels.RouteViewModel;
import com.razor.transit.viewmodels.StopViewModel;
import razor.android.transit.R;

import java.util.List;

public class StopListViewHolder implements EndlessScrollListener.OnLoadMoreDataListener,
                                            ViewModelListViewHolder.ViewModelListViewAdapterBuilder,
                                            ViewModelListAdapter.OnViewModelListAdapterListener {

    public static final int LayoutId = R.layout.lib_viewmodel_list_layout;

    public interface OnStopListViewListener{
        void onStopListItemSelected(StopViewModel viewModel);
    }

    private ViewModelListViewHolder viewModelListHolder = null;

    private int pageNumber = 1;
    private List<IViewModel> currentList = null;
    private OnStopListViewListener listener = null;

    public void setPageNumber(final Integer pageNumber){
        this.pageNumber = pageNumber;
    }

    public StopListViewHolder(final OnStopListViewListener listener,
                               final View parentView) {
        this.listener = listener;

        this.viewModelListHolder = new ViewModelListViewHolder(parentView);

        this.viewModelListHolder.setOnMoreDataListener(this);
        this.viewModelListHolder.setBuilder(this);
    }

    public void showLoading(final boolean show){
        this.viewModelListHolder.showLoading(show);
    }

    public void updateStops(final List<IViewModel> routes) {
        if (routes != null && routes.size() > 0) {
            this.viewModelListHolder.showNoResults(false);
            this.currentList = routes;
            if (this.pageNumber == 1) {
                this.viewModelListHolder.clearAdapter();
                this.viewModelListHolder.setAdapter(routes, this);
            } else {
                this.viewModelListHolder.updateAdapter(routes);
            }
        } else {
            if (this.pageNumber == 1) {
                this.viewModelListHolder.clearAdapter();
            }
        }

        if (this.viewModelListHolder.getSize() == 0){
            this.viewModelListHolder.showNoResults(true);
        }
    }

	/*
	 * Interface:
	 * ViewModelListViewHolder.ViewModelListViewAdapterBuilder
	 *  createAdapter(android.content.Context, java.util.List)
	 */

    @Override
    public ViewModelListAdapter createAdapter(final Context context,
                                              final List<IViewModel> models) {
        return new StopListAdapter(context, models);
    }

	/*
	 * Interface:
	 * EndlessScrollListener.OnLoadMoreDataListener
	 *  onScrollRequestsMoreData(int)
	 */

    @Override
    public void onScrollRequestsMoreData(final int page) {
        // do nothing
    }

	/*
	 * Interface
	 * ViewModelListAdapter.OnViewModelListAdapterListener
	 *  onListItemSelected(android.view.View)
	 */

    @Override
    public void onListItemSelected(final View view) {
        StopViewModel stop = (StopViewModel) view.getTag();
        this.listener.onStopListItemSelected(stop);
    }

}