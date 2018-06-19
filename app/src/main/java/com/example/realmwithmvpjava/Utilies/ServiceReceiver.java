package com.example.realmwithmvpjava.Utilies;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

public class ServiceReceiver extends BroadcastReceiver {

    private final static String TAG = ServiceReceiver.class.getSimpleName();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "OnServiceReceiver: ");
        Utility.scheduleJob(context);
    }
}
