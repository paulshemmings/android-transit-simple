package com.razor.transit.holders;

import android.content.Context;
import android.view.View;
import com.razor.transit.adapters.ArrivalTimesAdapter;
import com.razor.transit.adapters.ViewModelListAdapter;
import com.razor.transit.helpers.EndlessScrollListener;
import com.razor.transit.viewmodels.IViewModel;
import com.razor.transit.viewmodels.StopViewModel;
import razor.android.transit.R;

import java.util.List;

public class ArrivalTimeListViewHolder implements   EndlessScrollListener.OnLoadMoreDataListener,
                                                    ViewModelListViewHolder.ViewModelListViewAdapterBuilder,
                                                    ViewModelListAdapter.OnViewModelListAdapterListener {

    public static final int LayoutId = R.layout.lib_viewmodel_list_layout;

    public interface OnArrivalTimesViewListener{
        void onArrivalTimesItemSelected(StopViewModel viewModel);
    }

    private ViewModelListViewHolder viewModelListHolder = null;

    private int pageNumber = 1;
    private List<IViewModel> currentList = null;
    private OnArrivalTimesViewListener listener = null;

    public void setPageNumber(final Integer pageNumber){
        this.pageNumber = pageNumber;
    }

    public ArrivalTimeListViewHolder(final OnArrivalTimesViewListener listener,
                                     final View parentView) {
        this.listener = listener;

        this.viewModelListHolder = new ViewModelListViewHolder(parentView);

        this.viewModelListHolder.setOnMoreDataListener(this);
        this.viewModelListHolder.setBuilder(this);
    }

    public void showLoading(final boolean show){
        this.viewModelListHolder.showLoading(show);
    }

    public void updateArrivalTimes(final List<IViewModel> arrivalTimes) {
        if (arrivalTimes != null && arrivalTimes.size() > 0) {
            this.viewModelListHolder.showNoResults(false);
            this.currentList = arrivalTimes;
            if (this.pageNumber == 1) {
                this.viewModelListHolder.clearAdapter();
                this.viewModelListHolder.setAdapter(arrivalTimes, this);
            } else {
                this.viewModelListHolder.updateAdapter(arrivalTimes);
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
        return new ArrivalTimesAdapter(context, models);
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
    }

}