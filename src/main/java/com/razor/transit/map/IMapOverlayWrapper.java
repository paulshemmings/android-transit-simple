package com.razor.transit.map;

import com.google.android.maps.GeoPoint;
import com.razor.transit.viewmodels.IViewModel;

public interface IMapOverlayWrapper {

	int addItems(ViewModelOverlayItem[] items);

	int addItem(ViewModelOverlayItem item);

	int addItem(double lat, double lon, IViewModel model);

	void loadItems();

	int size();
	
	GeoPoint getOverlayCenter();
	
	int getOverlayLatitudeSpan();
	
	int getOverlayLongitudeSpan();	

}