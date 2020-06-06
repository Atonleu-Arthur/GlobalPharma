package com.example.globalpharma.controller;

import android.app.RemoteInput;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.globalpharma.Model.Alarm;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String answerTaken = intent.getStringExtra("Taken");
        String answerNoTaken = intent.getStringExtra("No Taken");

        Intent sendIntent = new Intent(context, AlarmThread.class);
        sendIntent.putExtra("Alarm", answerNoTaken + "-" + answerTaken);


    }
}
