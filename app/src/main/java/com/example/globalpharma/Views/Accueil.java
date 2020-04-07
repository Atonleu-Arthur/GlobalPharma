package com.example.globalpharma.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.app.Fragment;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.globalpharma.Model.Medication;
import com.example.globalpharma.R;

import java.util.ArrayList;
import java.util.List;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

public class Accueil extends AppCompatActivity {

    RecyclerView mRecyclerView;
    MedicationAdapter mAdapter;
    List<Medication> mMedicationList;
    androidx.fragment.app.Fragment fragmentMedication;
    Button mButtonAddMedication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        getSupportActionBar().hide();

        initActivity();

        mRecyclerView = findViewById(R.id.rv_medication);
        mMedicationList = new ArrayList<>();

        mMedicationList.add(new Medication("Doliprane", "Tete", "Comprimé", "2 comprimés", "12h00", null, "03 jours restants",
                R.mipmap.ic_edit_medication_foreground));
        mMedicationList.add(new Medication("Doliprane", "Tete", "Comprimé", "2 comprimés", "12h00", null, "03 jours restants",
                R.mipmap.ic_edit_medication_foreground));
        mMedicationList.add(new Medication("Doliprane", "Tete", "Comprimé", "2 comprimés", "12h00", null, "03 jours restants",
                R.mipmap.ic_edit_medication_foreground));
        mMedicationList.add(new Medication("Doliprane", "Tete", "Comprimé", "2 comprimés", "12h00", null, "03 jours restants",
                R.mipmap.ic_edit_medication_foreground));
        mMedicationList.add(new Medication("Doliprane", "Tete", "Comprimé", "2 comprimés", "12h00", null, "03 jours restants",
                R.mipmap.ic_edit_medication_foreground));
        mMedicationList.add(new Medication("Doliprane", "Tete", "Comprimé", "2 comprimés", "12h00", null, "03 jours restants",
                R.mipmap.ic_edit_medication_foreground));
        mMedicationList.add(new Medication("Doliprane", "Tete", "Comprimé", "2 comprimés", "12h00", null, "03 jours restants",
                R.mipmap.ic_edit_medication_foreground));
        mMedicationList.add(new Medication("Doliprane", "Tete", "Comprimé", "2 comprimés", "12h00", null, "03 jours restants",
                R.mipmap.ic_edit_medication_foreground));
        mMedicationList.add(new Medication("Doliprane", "Tete", "Comprimé", "2 comprimés", "12h00", null, "03 jours restants",
                R.mipmap.ic_edit_medication_foreground));
        mMedicationList.add(new Medication("Doliprane", "Tete", "Comprimé", "2 comprimés", "12h00", null, "03 jours restants",
                R.mipmap.ic_edit_medication_foreground));
        mMedicationList.add(new Medication("Doliprane", "Tete", "Comprimé", "2 comprimés", "12h00", null, "03 jours restants",
                R.mipmap.ic_edit_medication_foreground));
        mMedicationList.add(new Medication("Doliprane", "Tete", "Comprimé", "2 comprimés", "12h00", null, "03 jours restants",
                R.mipmap.ic_edit_medication_foreground));
        mMedicationList.add(new Medication("Doliprane", "Tete", "Comprimé", "2 comprimés", "12h00", null, "03 jours restants",
                R.mipmap.ic_edit_medication_foreground));

        mAdapter = new MedicationAdapter(this, mMedicationList);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mButtonAddMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Accueil.this, NewMedicationActivity.class);
                startActivity(intent);
            }
        });

    }

    public void initActivity() {
        //Elements
        mButtonAddMedication = findViewById(R.id.btn_add_medication);

        //Header
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_header_taking_medication, new Fragment())
                .commit()
        ;


        //Liste des prises
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_medications, new Fragment())
                .commit()
        ;

    }

    public void notify(String title, String message){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_add_treatment_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);




       /* NotificationCompat.Builder builder1 = new NotificationCompat.Builder(Accueil.this)
                .setSmallIcon(R.mipmap.ic_add_treatment_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true);

        Intent snoozeIntent = new Intent(this, NewMedicationActivity.class);
        snoozeIntent.setAction(Intent.ACTION_SCREEN_OFF);
        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
        PendingIntent snoozePendingIntent =
                PendingIntent.getBroadcast(this, 0, snoozeIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_add_treatment_foreground)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .addAction(R.drawable.ic_snooze, getString(R.string.text_pathology),
                        snoozePendingIntent);*/

    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.text_pathology);
            String description = getString(R.string.text_my_treatment);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("ds", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
