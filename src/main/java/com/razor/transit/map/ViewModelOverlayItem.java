package com.razor.transit.map;

import com.razor.transit.viewmodels.IViewModel;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public final class ViewModelOverlayItem extends OverlayItem {

	public ViewModelOverlayItem(GeoPoint point, String title, String snippet) {
		super(point, title, snippet);
	}
	
	public ViewModelOverlayItem(GeoPoint point, IViewModel model) {
		super(point, model.getTitle(), model.getDetails());
		this.setModel(model);
	}	
	
	private IViewModel model = null;
	public IViewModel getModel(){return this.model;}
	public void setModel(IViewModel model){this.model = model;}


}