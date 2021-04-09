package com.example.rafwalletproject.app;

import android.app.Application;

import androidx.appcompat.app.AppCompatDelegate;

import timber.log.Timber;

public class RafWalletApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO);
        }

}
