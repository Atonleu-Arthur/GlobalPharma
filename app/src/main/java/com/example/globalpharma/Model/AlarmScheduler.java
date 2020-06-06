package com.example.globalpharma.Model;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.net.Uri;
import android.os.Build;

public class AlarmScheduler {
    /**
     * @param context
     * @param reminderTask
     */

    public void setAlarm(Context context, long alarmTime, Uri reminderTask){
        AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);

        PendingIntent operation = AlarmService.getPendingIntent(context, reminderTask);

        if(Build.VERSION.SDK_INT >= 23){
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, alarmTime, operation);
        }
        else if(Build.VERSION.SDK_INT >= 19)
        {
            manager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, operation);
        }
        else{
            manager.set(AlarmManager.RTC_WAKEUP, alarmTime, operation);
        }
    }

    public void setRepeatAlarm(Context context, long alarmTime, Uri reminderTask, long repeatTime){
        AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);

        PendingIntent operation = AlarmService.getPendingIntent(context, reminderTask);

        manager.setRepeating(AlarmManager.RTC_WAKEUP,alarmTime, repeatTime, operation);
    }

    public void cancelAlarm(Context context, Uri reminderTask){
        AlarmManager manager = AlarmManagerProvider.getAlarmManager(context);

        PendingIntent operation = AlarmService.getPendingIntent(context, reminderTask);

        manager.cancel(operation);
    }


}
