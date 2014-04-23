package com.razor.transit.wrappers;

import android.content.Context;

public class ApplicationPreferenceWrapper {

    private ApplicationPreferenceWrapper preferences;

    public ApplicationPreferenceWrapper(final Context context) {
        // this.preferences = new ApplicationPreferences(context);
    }

    public ApplicationPreferenceWrapper getGeneralPreferences() {
        return preferences;
    }

}