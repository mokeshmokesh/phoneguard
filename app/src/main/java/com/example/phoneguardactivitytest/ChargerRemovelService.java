package com.example.phoneguardactivitytest;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class ChargerRemovelService extends Service {

    private BroadcastReceiver batteryReceiver;
    private MediaPlayer mediaPlayer;
    private static final String CHANNEL_ID="AntiTheftApp_Service";
    private static final int NOTIFICATION_ID=1;

    private BroadcastReceiver mScreenUnlockedReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (Intent.ACTION_USER_PRESENT.equals(intent.getAction())){
                stopSelf();
            }

        }
    };

    @SuppressLint("ForegroundServiceType")
    @Override
    public void onCreate() {
        super.onCreate();
        batteryReceiver = new BatteryReceiver();
        mediaPlayer = MediaPlayer.create(this, R.raw.sound);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"Anti-Theft App", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        Notification notification = new NotificationCompat.Builder(this,CHANNEL_ID)
                .setContentTitle("Charger Removal Detection Service")
                .setContentText("Service is running in the background")
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

        startForeground(NOTIFICATION_ID, notification);

        IntentFilter filter = new IntentFilter(Intent.ACTION_USER_PRESENT);
        registerReceiver(mScreenUnlockedReceiver, filter);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent,flags,startId);
        Toast.makeText(this, "Charger Removal Detection Service Started", Toast.LENGTH_SHORT).show();
        registerReceiver(batteryReceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Charger Removal Detection Service Stopped", Toast.LENGTH_SHORT).show();
        unregisterReceiver(batteryReceiver);
        mediaPlayer.stop();
        mediaPlayer.release();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }
    private class BatteryReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {

            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING || status == BatteryManager.BATTERY_STATUS_FULL;
            if (!isCharging){
                mediaPlayer.start();
            }

        }
    }

}