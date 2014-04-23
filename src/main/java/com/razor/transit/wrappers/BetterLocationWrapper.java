package com.razor.transit.wrappers;

import com.razor.transit.helpers.IBetterLocationListener;
import com.razor.transit.helpers.LocationHelper;
import com.razor.transit.helpers.LogHelper;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class BetterLocationWrapper implements LocationListener {

    protected LocationManager locationManager = null;
    protected Location currentLocation = null;
    protected IBetterLocationListener listener = null;

    public BetterLocationWrapper(){

    }

    public void monitorLocation(final Context context,
                                final IBetterLocationListener listener){
        try{
            this.listener = listener;
            this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            this.locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        } catch(Exception ex){
            LogHelper.e("LOCATION SERVICE FAILED", ex.getMessage());
        }
    }

    @Override
    public void onLocationChanged(final Location location) {
        if (LocationHelper.isBetterLocation(location, BetterLocationWrapper.this.currentLocation))
        {
            BetterLocationWrapper.this.currentLocation = location;
            if (this.listener != null) {
                listener.onLocationUpdated(location);
            }
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}


