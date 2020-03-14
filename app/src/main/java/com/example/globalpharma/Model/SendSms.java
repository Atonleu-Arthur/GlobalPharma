package com.example.globalpharma.Model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SendSms extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.sendBroadcast(intent);
    }
}
