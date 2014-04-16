package com.razor.transit.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;

import java.io.Serializable;

public class BaseActivity extends Activity
{
    protected ProgressDialog progressDialog = null;

    public String getIntentValue(String name){
        return getIntent().getStringExtra(name);
    }

    public Serializable getIntentSerializableValue(String name){
        return getIntent().getSerializableExtra(name);
    }

    public void setIntentValue(Intent i, String name, String value){
        i.putExtra(name, value);
    }

    public void setIntentValue(Intent i, String name, Serializable value){
        i.putExtra(name, value);
    }

    protected void showProgressDialog(final boolean show){
        this.showProgressDialog(show,"loading...");
    }
    protected void showProgressDialog(final boolean show, final int resourceId){
        String progressMessage = this.getResources().getString(resourceId);
        this.showProgressDialog(show, progressMessage);
    }

    protected void showProgressDialog(final boolean show, final String progressMessage){
        runOnUiThread(new Runnable() {
            public void run() {
                if (BaseActivity.this.progressDialog != null){
                    BaseActivity.this.progressDialog.dismiss();
                }
                if (show) {
                    BaseActivity.this.progressDialog = ProgressDialog.show(BaseActivity.this, null, progressMessage, true, false);
                }
            }
        });
    }
}
