package com.carsgates.cr.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.SparseIntArray;
import android.view.View;

import com.carsgates.cr.activities.AppBaseActivity;

/**
 * Created by Muhib.
 * Contact Number : +91 9796173066
 */
public abstract class RuntimePermissions extends AppBaseActivity{
    private SparseIntArray mErrorString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mErrorString=new SparseIntArray();
    }
    public void requestAppPermissions(final String[] requestedPermission, int stringId, final int requestCode)
    {
        mErrorString.put(requestCode,stringId);
        int permissionCheck= PackageManager.PERMISSION_GRANTED;
        boolean shouldShowRequestPermissionRationale=false;
        for (String permission:requestedPermission)
        {
            permissionCheck=permissionCheck+ ContextCompat.checkSelfPermission(this,permission);
            shouldShowRequestPermissionRationale=shouldShowRequestPermissionRationale|| ActivityCompat.shouldShowRequestPermissionRationale(this,permission);
        }
        if(permissionCheck!=PackageManager.PERMISSION_GRANTED)
        {
            if(shouldShowRequestPermissionRationale)
            {
                Snackbar.make(findViewById(android.R.id.content),stringId,Snackbar.LENGTH_INDEFINITE).setAction("GRANT", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityCompat.requestPermissions(RuntimePermissions.this,requestedPermission,requestCode);
                    }
                }).show();
            }
            else
            {
                ActivityCompat.requestPermissions(RuntimePermissions.this,requestedPermission,requestCode);
            }
        }
        else
        {
            onPermissiongranted(requestCode);
        }
    }



    protected abstract void onPermissiongranted(int requestCode);
}
