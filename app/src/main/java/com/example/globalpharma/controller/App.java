package com.example.globalpharma.controller;


import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.example.globalpharma.R;

public class App extends Application {
    public static final String CHANNEL_1 = String.valueOf(R.string.prise_medicament);
    public static final String CHANNEL_2 = "channel2";

    @Override
    public void onCreate(){
        super.onCreate();

        createNotificationChannels();
    }

    private void createNotificationChannels(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_1, String.valueOf(R.string.prise_medicament),
                    NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription(String.valueOf(R.string.prise_medicament));

            NotificationChannel channel2 = new NotificationChannel(CHANNEL_2, "Channel 2",
                    NotificationManager.IMPORTANCE_LOW);
            channel1.setDescription("Voici le channnel 2");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
        }
    }
}
