package com.razor.transit.applications;

import android.app.Application;
import android.content.Context;
import android.location.Location;
import com.razor.transit.helpers.IBetterLocationListener;
import com.razor.transit.wrappers.ApplicationPreferenceWrapper;
import com.razor.transit.wrappers.BetterLocationWrapper;
import razor.android.transit.R;

import java.util.UUID;

public class TransitApplication
        extends Application
        implements IBetterLocationListener
{

    private static TransitApplication instance;
    private static Context context;

    private Location currentBestLocation = null;
    private BetterLocationWrapper betterLocationWrapper = null;
    private String maximumMapZoom = null;
    private UUID privateUniqueIdentifier = null;
    private ApplicationPreferenceWrapper applicationPreferenceWrapper = null;

    public TransitApplication()
    {
        instance = this;
        this.applicationPreferenceWrapper = new ApplicationPreferenceWrapper(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        context =  getApplicationContext();

        // monitor the current location (disable monitoring for this release)
        this.betterLocationWrapper = new BetterLocationWrapper();
        this.betterLocationWrapper.monitorLocation(this, this);
    }

    /**
     * hacky way to get an instance of this
     * @return
     */

    public static TransitApplication instance() {
        return (TransitApplication) TransitApplication.instance;
    }

    @Override
    public void onLocationUpdated(final Location location) {
        this.setCurrentBestLocation(location);
    }

    public void setCurrentBestLocation(final Location currentBestLocation) {
        this.currentBestLocation = currentBestLocation;
    }

    public Location getCurrentBestLocation() {
        return currentBestLocation;
    }

    public UUID getUniqueIdentifier(){
        /*
        if (this.privateUniqueIdentifier == null) {
            String uuIdValue = this.applicationPreferenceWrapper
                    .getGeneralPreferences()
                    .getPreference("UUID", java.util.UUID.randomUUID().toString());
            this.privateUniqueIdentifiyer = java.util.UUID.fromString(uuIdValue);
        }
        */
        return this.privateUniqueIdentifier;
    }

    public float getMaximumMapZoom() {
        if (this.maximumMapZoom == null) {
            this.maximumMapZoom = this.getResources().getString(R.string.maximum_map_zoom);
        }
        return Float.valueOf(this.maximumMapZoom).floatValue();
    }

    public static Context getCoreApplicationContext() {
        return context;
    }

}