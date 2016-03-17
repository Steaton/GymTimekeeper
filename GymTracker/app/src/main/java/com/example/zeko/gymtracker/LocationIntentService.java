package com.example.zeko.gymtracker;

import android.app.IntentService;
import android.content.Intent;

public class LocationIntentService extends IntentService {

    public LocationIntentService() {
        super("LocationIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        System.out.println("This is the locatio:  XXX");
    }
}
