package com.carsgates.cr.activities;

import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.Locale;

/**
 * Created by ask on 11/15/2017.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Configuration configuration = getResources().getConfiguration();
            String languagecode = "ar";
            if (languagecode.equals("ar")) {
                configuration.setLayoutDirection(new Locale(languagecode));
            } else {
                configuration.setLayoutDirection(Locale.ENGLISH);
            }
            getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Configuration configuration = getResources().getConfiguration();
            String languagecode = "ar";
            if (languagecode.equals("ar")) {
                configuration.setLayoutDirection(new Locale(languagecode));
            } else {
                configuration.setLayoutDirection(Locale.ENGLISH);
            }
            getResources().updateConfiguration(configuration, getResources().getDisplayMetrics());
        }
    }
}
