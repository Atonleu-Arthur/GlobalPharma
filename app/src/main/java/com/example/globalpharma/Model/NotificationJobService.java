package com.example.globalpharma.Model;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

import com.example.globalpharma.R;
import com.example.globalpharma.Views.Accueil;

public class NotificationJobService extends JobService {
    Context context;
    Class newContext;

    public NotificationJobService(Context context, Class newContext) {
        this.context = context;
        this.newContext = newContext;
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                new Intent(context, newContext), PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle("Exemple")
                .setContentText("First notification")
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_add_treatment_foreground)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true)
                ;

        manager.notify(0, builder.build());

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
