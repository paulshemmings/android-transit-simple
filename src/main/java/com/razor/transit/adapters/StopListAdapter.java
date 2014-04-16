package com.razor.transit.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import com.razor.transit.viewmodels.IViewModel;
import razor.android.transit.R;

import java.util.List;

public class StopListAdapter extends ViewModelListAdapter {

    public StopListAdapter(final Context context,
                            final List<IViewModel> objects) {
        super(context, R.layout.lib_list_simple_item, objects);
    }

    public void renderRow(final View row,
                          final IViewModel model){
        if (model != null) {
            TextView tv = (TextView) row.findViewById(this.getMainTextId());
            tv.setText(model.getTitle());
            row.setTag(model);
        }
    }
}
