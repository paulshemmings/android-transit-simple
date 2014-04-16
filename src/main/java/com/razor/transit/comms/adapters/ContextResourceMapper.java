package com.razor.transit.comms.adapters;


import com.razor.transit.applications.TransitApplication;
import com.razor.transit.comms.IMessageResourceMapper;
import razor.android.transit.R;

public class ContextResourceMapper implements IMessageResourceMapper
{
    public String getMessageUrl(final int messageType) {
        return TransitApplication
                .getCoreApplicationContext()
                .getResources()
                .getString(messageType);
    }

    public String getRootUrl() {
        return TransitApplication
                .getCoreApplicationContext()
                .getResources()
                .getString(R.string.service_url);
    }
}
