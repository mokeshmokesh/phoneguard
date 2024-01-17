package com.example.phoneguardactivitytest;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class MotionDetectionService extends Service {
    public MotionDetectionService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}