package com.razor.transit.applications;

import android.app.Application;
import android.content.Context;

public class TransitApplication extends Application {

    private static TransitApplication instance;
    private static Context context;

    public TransitApplication()
    {
        instance = this;
    }

    public void onCreate()
    {
        super.onCreate();
        context =  getApplicationContext();
    }

    public static Context getCoreApplicationContext() {
        return context;
    }

    public static TransitApplication getCoreApplication(){
        return instance;
    }
}