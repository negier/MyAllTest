package com.xuebinduan.windowmanager;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

public class ForegroundService extends IntentService {


    public ForegroundService() {
        super("ForegroundService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        
    }
}