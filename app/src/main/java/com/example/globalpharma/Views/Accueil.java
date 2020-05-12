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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.globalpharma.Model.HourItem;
import com.example.globalpharma.Model.Medication;
import com.example.globalpharma.Model.MedicationPerDay;
import com.example.globalpharma.Model.MedicationPerDayAdapter;
import com.example.globalpharma.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

public class Accueil extends AppCompatActivity {

    private RecyclerView mRecyclerViewMedications;
    private TextView mTextView;
    private ImageView mButtonAddMedication;
    private MedicationPerDayAdapter mAdapter;
    private List<MedicationPerDay> mMedicationsPerDayList;
    private List<Medication> mMedicationList;
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);

        getSupportActionBar().hide();

        initActivity();

        initRecyclerViewMedications();

        setNewMedications();

        passToNewMedicationActivity();

    }

    public void initActivity() {
        //Elements
        mButtonAddMedication = findViewById(R.id.btn_add_medication);
        mTextView = findViewById(R.id.text_my_treatments);
        mRecyclerViewMedications = findViewById(R.id.rv_medication);

        //Header
        /*getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_header_taking_medication, new Fragment())
                .commit()
        ;

        */
        //Liste des prises
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_medication, new Fragment())
                .commit()
        ;

    }

    public String getNewMedications(){
        Intent intent = getIntent();
        if(intent.hasExtra("medications List")){
            return intent.getStringExtra("medications List");
        }
        else
            return null;
    }

    public void setNewMedications(){
        if(getNewMedications() != null){
            Gson gson = new Gson();
            Type medicationListType = new TypeToken<List<Medication>>(){}.getType();
            List<Medication> medications = gson.fromJson(getNewMedications(), medicationListType);
            for (Medication medication :
                    medications) {
                mMedicationList.add(medication);
            }
            mAdapter = new MedicationPerDayAdapter(mMedicationsPerDayList, Accueil.this);
            mRecyclerViewMedications.setAdapter(mAdapter);
        }
    }

    private void initRecyclerViewMedications() {
        mMedicationsPerDayList = new ArrayList<>();
        mMedicationList = new ArrayList<>();

        mMedicationList.add(new Medication("A", "b", "c", "d", "n", null, null, R.mipmap.ic_delete_medication_foreground));
        mMedicationList.add(new Medication("A", "b", "c", "d", "n", null, null, R.mipmap.ic_delete_medication_foreground));
        mMedicationList.add(new Medication("A", "b", "c", "d", "n", null, null, R.mipmap.ic_delete_medication_foreground));
        mMedicationList.add(new Medication("A", "b", "c", "d", "n", null, null, R.mipmap.ic_delete_medication_foreground));
        mMedicationList.add(new Medication("A", "b", "c", "d", "n", null, null, R.mipmap.ic_delete_medication_foreground));
        mMedicationList.add(new Medication("A", "b", "c", "d", "n", null, null, R.mipmap.ic_delete_medication_foreground));
        mMedicationList.add(new Medication("A", "b", "c", "d", "n", null, null, R.mipmap.ic_delete_medication_foreground));
        mMedicationList.add(new Medication("A", "b", "c", "d", "n", null, null, R.mipmap.ic_delete_medication_foreground));
        mMedicationList.add(new Medication("A", "b", "c", "d", "n", null, null, R.mipmap.ic_delete_medication_foreground));
        mMedicationList.add(new Medication("A", "b", "c", "d", "n", null, null, R.mipmap.ic_delete_medication_foreground));
        mMedicationList.add(new Medication("A", "b", "c", "d", "n", null, null, R.mipmap.ic_delete_medication_foreground));

        mMedicationsPerDayList.add(new MedicationPerDay("Lundi, 20 Avril 2020", mMedicationList));
        mMedicationsPerDayList.add(new MedicationPerDay("Lundi, 20 Avril 2020", mMedicationList));
        mMedicationsPerDayList.add(new MedicationPerDay("Lundi, 20 Avril 2020", mMedicationList));
        mMedicationsPerDayList.add(new MedicationPerDay("Lundi, 20 Avril 2020", mMedicationList));
        mMedicationsPerDayList.add(new MedicationPerDay("Lundi, 20 Avril 2020", mMedicationList));
        mMedicationsPerDayList.add(new MedicationPerDay("Lundi, 20 Avril 2020", mMedicationList));
        mMedicationsPerDayList.add(new MedicationPerDay("Lundi, 20 Avril 2020", mMedicationList));
        mMedicationsPerDayList.add(new MedicationPerDay("Lundi, 20 Avril 2020", mMedicationList));
        mMedicationsPerDayList.add(new MedicationPerDay("Lundi, 20 Avril 2020", mMedicationList));
        mMedicationsPerDayList.add(new MedicationPerDay("Lundi, 20 Avril 2020", mMedicationList));
        mMedicationsPerDayList.add(new MedicationPerDay("Lundi, 20 Avril 2020", mMedicationList));
        mMedicationsPerDayList.add(new MedicationPerDay("Lundi, 20 Avril 2020", mMedicationList));
        mMedicationsPerDayList.add(new MedicationPerDay("Lundi, 20 Avril 2020", mMedicationList));
        mMedicationsPerDayList.add(new MedicationPerDay("Lundi, 20 Avril 2020", mMedicationList));

        mAdapter = new MedicationPerDayAdapter(mMedicationsPerDayList, Accueil.this);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerViewMedications.setAdapter(mAdapter);
        mRecyclerViewMedications.setLayoutManager(mLinearLayoutManager);
    }

    private void passToNewMedicationActivity() {
        mButtonAddMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Accueil.this, NewMedicationActivity.class);
                startActivity(intent);
            }
        });
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
