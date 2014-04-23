package com.razor.transit.helpers;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

public class MapHelper {

    public static GeoPoint getPoint(double lat, double lon) {
        return (new GeoPoint((int) (lat * 1000000.0), (int) (lon * 1000000.0)));
    }

    public static OverlayItem createOverlayItem(double lat, double lon, String title, String snippet){
        return new OverlayItem(getPoint(lat,lon), title, snippet);
    }

}
