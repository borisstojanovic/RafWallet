package raf.rs.projekat1.boris_stojanovic_rn3518.app;

import android.app.Application;

import timber.log.Timber;

public class RafWalletApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Timber.plant(new Timber.DebugTree());
    }
}
