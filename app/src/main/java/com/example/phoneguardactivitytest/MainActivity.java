package com.example.phoneguardactivitytest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button chargerRemovalButton;

    private static final String CHANNEL_ID = "AntiTheftApp";

    private static final int NOTIFICATION_ID = 1 ;

    private boolean chargerServiceStarted = false;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}