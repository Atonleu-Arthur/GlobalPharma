package com.example.globalpharma.Model;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.globalpharma.R;
import com.example.globalpharma.Views.Accueil;
import com.example.globalpharma.Views.NewMedicationActivity;

public class AlarmService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    private static final String TAG = AlarmService.class.getSimpleName();

    private static final int NOTIFICATION_ID = 42;

    public static PendingIntent getPendingIntent(Context context, Uri uri){
        Intent action = new Intent(context, AlarmService.class);
        action.setData(uri);
        return PendingIntent.getService(context, 0, action, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public AlarmService(String name) {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Uri uri = intent.getData();

        Intent action = new Intent(this, Accueil.class);
        action.setData(uri);

        PendingIntent operation = TaskStackBuilder.create(this)
                .addNextIntentWithParentStack(action)
                .getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        String description = "Welcome to the sale";
        try{
            if(cursor != null && cursor.moveToFirst()){
                description = AlarmContract.getColumnString(cursor, AlarmContract.AlarmEntry.KEY_TITLE);
            }
        }
        finally {
            if (cursor == null){
                cursor.close();
            }
        }

        Notification note = new NotificationCompat.Builder(this)
                .setContentTitle("Prise de médicaments")
                .setContentText(description)
                .setSmallIcon(R.drawable.common_google_signin_btn_icon_dark)
                .setContentIntent(operation)
                .setAutoCancel(true)
                .build();

        manager.notify(NOTIFICATION_ID, note);
    }


}
