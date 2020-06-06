package com.example.globalpharma.controller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.globalpharma.Model.Alarm;
import com.example.globalpharma.Model.Medication;
import com.example.globalpharma.Views.NewMedicationActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class AlarmThread extends Thread implements Runnable  {
    private Context context;
    private Medication medication;
    private Alarm alarm;
    private Intent mIntent;

    public AlarmThread(Context context, Alarm alarm) {
        this.context = context;
        this.alarm = alarm;
    }

    @Override
    public void run() {
       if (Date.parse(new SimpleDateFormat().format("dd-mm-yyyy")) <= Date.parse(alarm.getEndingDate())) {
           AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
           Intent intent = new Intent(context, AlertReceiver.class);
           PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, 0);

           alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarm.getTriggerMillis(), alarm.getRepeatTime(), pendingIntent);
       }
    }

    public void notifyAlarm(String title, String content){
        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotificationForALert(title, content);
        notificationHelper.getManager().notify(1, nb.build());
    }
}
