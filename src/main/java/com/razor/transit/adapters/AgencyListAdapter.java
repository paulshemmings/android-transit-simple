package com.razor.transit.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.razor.transit.viewmodels.AgencyViewModel;
import com.razor.transit.viewmodels.IViewModel;
import razor.android.transit.R;

import java.util.List;

public class AgencyListAdapter extends ViewModelListAdapter {

    public AgencyListAdapter(final Context context,
                             final List<IViewModel> objects) {
        super(context, R.layout.agency_list_item_layout, objects);
    }

    public void renderRow(final View row,
                          final IViewModel model){
        if (model != null) {
            TextView tv = (TextView) row.findViewById(this.getMainTextId());
            tv.setText(model.getTitle());
/*
            TextView stv = (TextView) row.findViewById(this.getSubTextId());
            stv.setText(agencyViewModel.getModel().getCreateDateTime().toString());

            if (agencyViewModel.getModel().getImageData()!=null) {

                byte[] bitmapdata = agencyViewModel.getModel().getImageData();
                Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapdata , 0, bitmapdata .length);
                Bitmap icon = new ImageWrapper().resizeImage(bitmap, 150);

                ImageView agencyImageView = (ImageView) row.findViewById(R.id.agency_image_view);
                agencyImageView.setImageBitmap(icon);
            }
*/
            row.setTag(model);
        }
    }

}
