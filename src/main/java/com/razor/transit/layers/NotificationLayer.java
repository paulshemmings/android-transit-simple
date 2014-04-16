package com.razor.transit.layers;

import android.app.ProgressDialog;
import android.app.Activity;

public class NotificationLayer {

    private ProgressDialog progressDialog = null;

    public void showLoadingDialog(final Activity context,
                                      final boolean show) {
        this.showProgressDialog(context, show, "loading...");
    }

    public void showProgressDialog(final Activity context,
                                      final boolean show,
                                      final int resourceId) {
        String progressMessage = context.getResources().getString(resourceId);
        this.showProgressDialog(context, show, progressMessage);
    }

    public void showProgressDialog(final Activity context,
                                      final boolean show,
                                      final String progressMessage){
        context.runOnUiThread(new Runnable() {
            public void run() {
                if (NotificationLayer.this.progressDialog != null) {
                    NotificationLayer.this.progressDialog.dismiss();
                }
                if (show) {
                    NotificationLayer.this.progressDialog = ProgressDialog.show(
                            context,
                            null,
                            progressMessage,
                            true,
                            false);
                }
            }
        });
    }

}
