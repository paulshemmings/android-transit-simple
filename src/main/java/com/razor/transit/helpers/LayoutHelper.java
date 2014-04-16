package com.razor.transit.helpers;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;

public class LayoutHelper {

    public static View setTemplateContentView(final Activity activity,
                                              final int templateID,
                                              final int containerID,
                                              final int contentID) {

        // inflate the template
        LayoutInflater li = activity.getLayoutInflater();
        View templateView = li.inflate(templateID, null);

        // inflate the content
        View activityContent = li.inflate(contentID, null);

        // set the view to the template
        activity.setContentView(templateView);

        // add the content
        ((LinearLayout) templateView.findViewById(containerID)).addView(activityContent);

        return templateView; // activityContent;
    }

    public static void setStubView(final Activity activity,
                                   final int stubID,
                                   final int layoutID){
        ViewStub stub = (ViewStub) activity.findViewById(stubID);
        stub.setLayoutResource(layoutID);
        stub.inflate();
    }

}
