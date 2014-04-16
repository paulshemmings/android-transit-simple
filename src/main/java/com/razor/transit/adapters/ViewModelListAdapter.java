package com.razor.transit.adapters;

import android.widget.ArrayAdapter;
import com.razor.transit.viewmodels.IViewModel;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import razor.android.transit.R;

import java.util.List;

public class ViewModelListAdapter extends ArrayAdapter<IViewModel> implements View.OnClickListener {

    public interface OnViewModelListAdapterListener{
        public void onListItemSelected(View view);
    }
    public interface ViewModelListAdapterRenderer{
        public void renderRow(View row, IViewModel item);
    }

    private OnViewModelListAdapterListener listener;
    private ViewModelListAdapterRenderer renderer;

    private int mainTextId = R.id.lib_list_item_main_text;
    private int subTextId = R.id.lib_list_item_sub_text;
    private int rowLayoutId = R.layout.lib_list_simple_item;

    public ViewModelListAdapter(final Context context, final List<IViewModel> objects) {
        super(context, R.layout.lib_list_layout, objects);
    }

    public ViewModelListAdapter(final Context context,
                                final int rowLayoutId,
                                final List<IViewModel> objects) {
        super(context, rowLayoutId, objects);
        this.rowLayoutId = rowLayoutId;
    }

    public ViewModelListAdapter(final Context context,
                                final int rowLayoutId,
                                final int mainTextId,
                                final int subTextId,
                                final List<IViewModel> models) {
        super(context, rowLayoutId, models);
        this.rowLayoutId = rowLayoutId;
        this.mainTextId = mainTextId;
        this.subTextId = subTextId;
    }

    public int getRowLayoutId(final int id){ return this.rowLayoutId;}
    public void setRowLayoutId(final int id){ this.rowLayoutId = id;}

    public int getMainTextId(){ return this.mainTextId;}
    public void setMainTextId(final int id){ this.mainTextId = id;}

    public int getSubTextId(){ return this.subTextId;}
    public void setSubTextId(final int id){ this.subTextId = id;}

    public void addRange(final IViewModel[] models){
        for (IViewModel model:models){
            this.add(model);
        }
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View row;

        if (null == convertView) {
            LayoutInflater li = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = li.inflate(this.rowLayoutId, null);
        } else {
            row = convertView;
        }

        IViewModel item = getItem(position);

        if (this.renderer == null) {
            this.renderRow(row, item);
        } else {
            this.renderer.renderRow(row, item);
        }

        if (this.listener != null) {
            row.setOnClickListener(this);
        }
        return row;
    }

    public void renderRow(final View row, final IViewModel item){
        if (item != null) {
            TextView tv = (TextView) row.findViewById(this.mainTextId);
            tv.setText(item.getTitle());

            TextView stv = (TextView) row.findViewById(this.subTextId);
            stv.setText(item.getDetails());

            row.setTag(item);
        }
    }

    public void setListener(final OnViewModelListAdapterListener listener) {
        this.listener = listener;
    }

    public OnViewModelListAdapterListener getListener() {
        return listener;
    }

    public void onClick(final View v) {
        if (ViewModelListAdapter.this.listener != null){
            ViewModelListAdapter.this.listener.onListItemSelected(v);
        }
    }

}
