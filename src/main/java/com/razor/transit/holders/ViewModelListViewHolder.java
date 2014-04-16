package com.razor.transit.holders;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import com.razor.transit.adapters.ViewModelListAdapter;
import com.razor.transit.helpers.EndlessScrollListener;
import com.razor.transit.viewmodels.IViewModel;
import com.razor.transit.views.ListFooterView;
import razor.android.transit.R;

public class ViewModelListViewHolder {

    public interface ViewModelListViewAdapterBuilder{
        ViewModelListAdapter createAdapter(Context context, List<IViewModel> models);
    }

    private View parentView = null;
    private ListView modelList = null;
    private ViewModelListAdapter adapter = null;
    private EndlessScrollListener scrollListener = null;
    private List<IViewModel> modelStore = null;
    private ViewModelListViewAdapterBuilder builder = null;
    private ListFooterView vFooter = null;
    private TextView noResultMessage = null;

    public ViewModelListViewHolder(final View view){
        this.parentView = view;
        this.scrollListener = new EndlessScrollListener(2);
        this.modelList = (ListView) view.findViewById(R.id.viewmodel_list);
        this.modelList.setOnScrollListener(this.scrollListener);

        this.noResultMessage = (TextView) view.findViewById(R.id.no_results);
        this.vFooter = new ListFooterView(view.getContext());
        this.modelList.addFooterView(vFooter, null, false);
    }

    public ListView getModelList(){
        return this.modelList;
    }

    public int getSize(){
        return this.modelStore != null ? this.modelStore.size() : 0;
    }

    public void setAdapter(final List<IViewModel> models,
                           final ViewModelListAdapter.OnViewModelListAdapterListener listener){

        if (this.modelStore == null) {
            this.modelStore = new ArrayList<IViewModel>();
        }
        this.modelStore.addAll(models);

        // build the adapter
        if (this.builder == null) {
            this.adapter = new ViewModelListAdapter(this.parentView.getContext(), this.modelStore);
        } else{
            this.adapter = this.builder.createAdapter(this.parentView.getContext(), this.modelStore);
        }

        this.adapter.setListener(listener);
        this.modelList.setAdapter(this.adapter);
        this.modelList.refreshDrawableState();
    }

    public void updateAdapter(final List<IViewModel> moreModels){
        if (this.modelStore != null && this.adapter != null) {
            this.modelStore.addAll(moreModels);
            this.adapter.notifyDataSetChanged();
        }
    }

    public void clearAdapter(){
        if (this.modelStore != null) {
            this.modelStore.clear();

        }
        if (this.adapter != null) {
            this.adapter.notifyDataSetChanged();
        }
        if (this.scrollListener != null) {
            this.scrollListener.reset();
        }
    }

    public void setOnMoreDataListener(final EndlessScrollListener.OnLoadMoreDataListener
                                              moreDataListener){
        this.scrollListener.setMoreDataListener(moreDataListener);
    }

    public void setBuilder(final ViewModelListViewAdapterBuilder builder) {
        this.builder = builder;
    }

    public ViewModelListViewAdapterBuilder getBuilder() {
        return builder;
    }

    public void showLoading(final boolean show){
        if (show) {
            this.showNoResults(false);
        }
        if (this.vFooter != null) {
            this.vFooter.showLoading(show);
        }
    }

    public void showNoResults(final boolean show){
        if(this.modelList != null && this.noResultMessage != null){
            if (!show){
                this.modelList.setVisibility(View.VISIBLE);
                this.noResultMessage.setVisibility(View.GONE);
            }
            else{
                this.modelList.setVisibility(View.GONE);
                this.noResultMessage.setVisibility(View.VISIBLE);
            }
        }
    }
}
