package com.razor.transit.holders;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.razor.transit.adapters.RouteListAdapter;
import com.razor.transit.adapters.ViewModelListAdapter;
import com.razor.transit.helpers.EndlessScrollListener;
import com.razor.transit.viewmodels.IViewModel;
import com.razor.transit.viewmodels.RouteViewModel;
import razor.android.transit.R;

import java.util.List;

public class RouteListViewHolder implements EndlessScrollListener.OnLoadMoreDataListener,
                                            ViewModelListViewHolder.ViewModelListViewAdapterBuilder,
                                            View.OnClickListener,
                                            View.OnLongClickListener,
                                            ViewModelListAdapter.OnViewModelListAdapterListener,
                                            TextView.OnEditorActionListener {

    public static final int LayoutId = R.layout.lib_viewmodel_list_layout;

    public interface OnRouteListViewRouteListener{
        void onNearbyRoutesRequested();
        void onNoRoutesFound();
        void onRouteListItemSelected(RouteViewModel viewModel);
        void onRouteMapRequested(List<IViewModel> items);
    }

    private ViewModelListViewHolder viewModelListHolder = null;

    private int pageNumber = 1;
    private List<IViewModel> currentList = null;
    private OnRouteListViewRouteListener listener = null;

    public void setPageNumber(final Integer pageNumber){
        this.pageNumber = pageNumber;
    }

    public RouteListViewHolder(final OnRouteListViewRouteListener listener,
                               final View parentView) {
        this.listener = listener;

        this.viewModelListHolder = new ViewModelListViewHolder(parentView);

        this.viewModelListHolder.setOnMoreDataListener(this);
        this.viewModelListHolder.setBuilder(this);
    }

    public void showLoading(final boolean show){
        this.viewModelListHolder.showLoading(show);
    }

    public void updateRoutes(final List<IViewModel> routes){
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
            this.listener.onNoRoutesFound();
        }

        if (this.viewModelListHolder.getSize() == 0){
            this.viewModelListHolder.showNoResults(true);
            this.listener.onNoRoutesFound();
        }
    }

	/*
	 * Interface:
	 * @see android.view.View.OnLongClickListener
	 *  onClick(android.view.View)
	 */

    @Override
    public boolean onLongClick(final View v) {
        this.listener.onRouteMapRequested(this.currentList);
        return this.currentList != null;
    }

	/*
	 * Interface:
	 * @see android.view.View.OnClickListener
	 *  onClick(android.view.View)
	 */

    @Override
    public void onClick(final View v) {
        // do nothing
    }

	/*
	 * Interface:
	 * ViewModelListViewHolder.ViewModelListViewAdapterBuilder
	 *  createAdapter(android.content.Context, java.util.List)
	 */

    @Override
    public ViewModelListAdapter createAdapter(final Context context,
                                              final List<IViewModel> models) {
        return new RouteListAdapter(context, models);
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
        RouteViewModel routeViewModel = (RouteViewModel) view.getTag();
        this.listener.onRouteListItemSelected(routeViewModel);
    }

	/*
	 * Inteface
	 * TextView.OnEditorActionListener
	 *  onEditorAction(android.widget.TextView, int, android.view.KeyRoute)
	 */

    @Override
    public boolean onEditorAction(final TextView v,
                                  final int actionId,
                                  final KeyEvent route) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            this.pageNumber = 1;
            // this.listener.onMoreRoutesRequested(this.pageNumber);
            return true;
        }
        return false;
    }

    private static void hideKeyboard(final View view) {
        Context context = view.getContext();
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
