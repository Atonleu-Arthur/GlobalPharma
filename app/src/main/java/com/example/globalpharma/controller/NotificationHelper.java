package com.example.globalpharma.controller;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.globalpharma.R;

public class NotificationHelper  extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";
    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification(String title, String content) {
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle(title)
                .setContentText(content)
                .setSubText(getText(R.string.prise_medicament))
                .setSmallIcon(R.drawable.logo);
    }

    public NotificationCompat.Builder getChannelNotificationForALert(String title, String content) {
        Intent broadcastTakenIntent = new Intent(getApplicationContext(), NotificationReceiver.class);
        broadcastTakenIntent.putExtra("Taken", getText(R.string.notify_btn_taken));
        PendingIntent acionTakenIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, broadcastTakenIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        Intent broadcastNoTakenIntent = new Intent(getApplicationContext(), NotificationReceiver.class);
        broadcastNoTakenIntent.putExtra("No Taken", getText(R.string.notify_btn_no_taken));
        PendingIntent acionNoTakenIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, broadcastNoTakenIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle(title)
                .setContentText(content)
                .setSubText(getText(R.string.prise_medicament))
                .addAction(R.mipmap.ic_launcher, getText(R.string.notify_btn_taken), acionTakenIntent)
                .addAction(R.mipmap.ic_launcher, getText(R.string.notify_btn_no_taken), acionNoTakenIntent)
                .setSmallIcon(R.drawable.logo);
    }
}
