package com.tangex.admin.sexology_text;

import android.app.Application;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.firebase.database.FirebaseDatabase;

public class Offline_Service extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
