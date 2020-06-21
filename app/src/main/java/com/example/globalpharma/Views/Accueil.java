package com.example.globalpharma.Views;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globalpharma.Model.MedicationPerDay;
import com.example.globalpharma.Model.Medicine;
import com.example.globalpharma.R;
import com.example.globalpharma.controller.AlertReceiver;
import com.example.globalpharma.controller.MedicationPerDayAdapter;
import com.example.globalpharma.controller.NotificationHelper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Accueil extends AppCompatActivity {

    private RecyclerView mRecyclerViewMedications;
    private TextView mTextView;
    private ImageView mButtonAddMedication;
    private ImageView mDeleteItem;
    private ImageView mEditItem;
    private MedicationPerDayAdapter mAdapter;
    private List<MedicationPerDay> mMedicationsPerDayList;
    private List<Medicine> mMedicationList;
    private LinearLayoutManager mLinearLayoutManager;
    private MedicationAdapter mMedicationAdapter;

    private ConstraintLayout mLayoutDay;

    private MedicineAdapter mMedicineAdapter;

    private Button mButton;
    private RecyclerView mRecyclerView;

    public static final String SP_NAME_STRING = "MedicationsPerDay";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            setContentView(R.layout.activity_accueil);
        }
        catch (Exception e){
            Log.d(".......", e.toString());
        }

        getSupportActionBar().hide();

        /*initActivity();


        //addAlarm(mMedicationList);

        //setAlarmNottification(mMedicationList);

        initRecyclerViewMedications();

        passToNewMedicationActivity();

        deleteMedication();

        editMedication();*/

        initView();

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Accueil.this, NewMedicationActivity.class));
                finish();
            }
        });

        retrieveFromSharedPreferences(SP_NAME_STRING);

        setRv();

        manageItems();

        hideDates();

        getNewItems();

        getItemEdited();

        cancelAlarm();

        for (Medicine medicine:
             mMedicationList) {
            setAlarm(medicine);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (Medicine medicine:
                mMedicationList) {
            //if(new Date().getTime() <= Date.parse(medicine.getEndingDate())) {
                if (new SimpleDateFormat("dd-mm-yyyy").format(Calendar.getInstance().getTime()).startsWith(medicine.getHourItem().getHourValue().replace(" ", ""))) {
                    notifyAlarm(medicine.getName(), medicine.getPathology(), medicine.getMoment(), medicine.getQuantityPerDay() + " " + medicine.getForm());
                }
        }

    }

    /*private void editMedication() {
    }

    private void deleteMedication() {
        mMedicationAdapter.setOnItemClickListener(new MedicationAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                Log.d("Position", " fef-" + position);
            }

            @Override
            public void onEditClick(int position) {
                Medication medication = mMedicationList.get(position);
                startActivity(new Intent(Accueil.this, NewMedicationActivity.class).putExtra("Edit item", new Gson().toJson(medication)));
            }
        });

    }

    public void initActivity() {
        mMedicationsPerDayList = new ArrayList<>();
        mMedicationList = new ArrayList<>();
        mMedicationAdapter = new MedicationAdapter(mMedicationList);

        //Elements
        mButtonAddMedication = findViewById(R.id.btn_add_medication);
        mTextView = findViewById(R.id.text_my_treatments);
        mRecyclerViewMedications = findViewById(R.id.rv_medication);
        mDeleteItem = findViewById(R.id.img_delete);
        mEditItem = findViewById(R.id.img_edit);

        //Header
        /*getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_header_taking_medication, new Fragment())
                .commit()
        ;


        //Liste des prises
        getFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_medication, new Fragment())
                .commit()
        ;

    }

    /*public String getNewMedications(){
        Intent intent = getIntent();
        if(intent.hasExtra("medicationsList")){
            return intent.getStringExtra("medicationsList");
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
        }
    }*/

    /*private void initRecyclerViewMedications() {
        retrieveFromSharedPreferences(SP_NAME_STRING);

        int days = 0;
        if(!mMedicationList.isEmpty()) {
            try{
                 days = Integer.parseInt(mMedicationList.get(0).getTreatmentDuration());
            }
            catch (Exception e){
                Log.d("Exception: ", e.toString());
            }
            for (int i = 0; i < days; i++){
                Date date = null;
                try {
                    date = DateFormat.getDateInstance(DateFormat.FULL).parse(mMedicationList.get(0).getStartingDate());
                } catch (ParseException e) {
                    Log.d("Exception de date: ", e.toString());
                }
                date.setDate(date.getDate() + i);
                mMedicationsPerDayList.add(new MedicationPerDay(DateFormat.getDateInstance(DateFormat.FULL).format(date), mMedicationList));
            }
        }

        //retrieveFromSharedPreferences(SP_NAME_STRING);

        mAdapter = new MedicationPerDayAdapter(mMedicationsPerDayList, Accueil.this);
        mLinearLayoutManager = new LinearLayoutManager(this);

        mRecyclerViewMedications.setAdapter(mAdapter);
        mRecyclerViewMedications.setLayoutManager(mLinearLayoutManager);

        mTextView.setText(mMedicationsPerDayList.size() + " items " + mMedicationList.size());

        //addAlarm(mMedicationList);

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
                        snoozePendingIntent);

    }

    private void setAlarmNotification(List<Medication> medications){
        if (!medications.isEmpty()){
            for (Medication medication :
                    medications) {
                if((Date.parse(medication.getMedicationHour()) == Date.parse(new SimpleDateFormat("dd/mm/yyyy").toString()))){
                    notifyAlarm(medications.indexOf(medication), medication);
                }
            }
        }
    }

    private void notifyAlarm(int id, Medication medication) {
        String title = medication.getMedicationName();
        String content = medication.getTakingMoment() + " " + medication.getMedicationPrecision();
        NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
        NotificationCompat.Builder nb = notificationHelper.getChannelNotificationForALert(title, content);
        if((Date.parse(new SimpleDateFormat("dd/mm/yyyy").toString())) == (Date.parse(medication.getMedicationHour())))
            notificationHelper.getManager().notify(id, nb.build());

        /*Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

        Intent broadcastIntent = new Intent(getApplicationContext(), NotificationReceiver.class);
        broadcastIntent.putExtra("Action", "Text");

        PendingIntent acionIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(getApplicationContext(), "Channel1")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Title")
                .setContentText("Text")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_REMINDER)
                .setContentIntent(pendingIntent)
                .addAction(R.mipmap.ic_launcher, "Pris", acionIntent)
                .addAction(R.mipmap.ic_launcher, "Non Pris", acionIntent)
                .setColor(Color.RED)
                .build();

        notificationManagerCompat.notify(1, notification);
    }

    private void addAlarm(List<Medication> medications){
        AlarmManager alarmManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        }

        //creating a new intent specifying the broadcast receiver
        Intent i = new Intent(this, AlertReceiver.class);

        //creating a pending intent using the intent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, i, 0);

        //setting the repeating alarm that will be fired every day
        if(!medications.isEmpty()){
            for (Medication medication:
                    medications) {
                alarmManager.setRepeating(AlarmManager.RTC, Date.parse(medication.getMedicationHour()), AlarmManager.INTERVAL_DAY, pendingIntent);
            }
        }
    }*/

    private void retrieveFromSharedPreferences(String nameString){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        if(sharedPreferences.contains(SP_NAME_STRING)){
            Gson gson = new Gson();
            String json = sharedPreferences.getString(nameString, null);
            Type type = new TypeToken<List<Medicine>>() {}.getType();
            mMedicationList = gson.fromJson(json, type);
            if (mMedicationList == null) {
                mMedicationList = new ArrayList<>();
            }
        }
    }

    private void updateRecylcerView() {
        mAdapter = new MedicationPerDayAdapter(mMedicationsPerDayList, Accueil.this);
        mLinearLayoutManager = new LinearLayoutManager(this);

        mRecyclerViewMedications.setAdapter(mAdapter);
        mRecyclerViewMedications.setLayoutManager(mLinearLayoutManager);

        mTextView.setText(mMedicationsPerDayList.size() + " items " + mMedicationList.size());

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

    private void initView(){
        mButton = findViewById(R.id.btn_add);
        mRecyclerView = findViewById(R.id.rv_medicines);
        mLayoutDay = findViewById(R.id.cl_day);
        mMedicationList = new ArrayList<>();
    }

    private void setRv(){
        mMedicineAdapter = new MedicineAdapter(mMedicationList);
        mLinearLayoutManager = new LinearLayoutManager(this);

        mRecyclerView.setAdapter(mMedicineAdapter);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
    }

    private void manageItems(){
        mMedicineAdapter.setOnItemClickListener(new MedicineAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                mMedicationList.remove(position);
                mMedicineAdapter.notifyItemRemoved(position);
                storeToSharedPreferences();
            }

            @Override
            public void onEditClick(int position) {
                Medicine medicine = mMedicationList.get(position);
                Intent intent = new Intent(Accueil.this, NewMedicationActivity.class);
                intent.putExtra("MedicationToEdit", new Gson().toJson(medicine));
                intent.putExtra("position", new Gson().toJson(position));
                //mMedicationList.remove((position));

                startActivity(intent);
            }
        });
    }

    public void hideDates() {
        int i = 0;
        int j = 0;
        for (int s = 1; s < mMedicationList.size(); s++) {
            if (mMedicationList.get(s).getDate() == mMedicationList.get(i).getDate()) {
                mLayoutDay.setVisibility(View.GONE);
                j++;
            }
            else{
                i = j;
            }

        }
    }

    private void storeToSharedPreferences(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mMedicationList);
        editor.putString(SP_NAME_STRING, json);
        editor.apply();
    }

    private void getItemEdited(){
        if(getIntent().hasExtra("ItemModified") && getIntent().hasExtra("positionEditedItem")){
            Type type = new TypeToken<List<Medicine>>(){}.getType();
            Type typeInt = new TypeToken<Integer>(){}.getType();
            List<Medicine> medicines = new Gson().fromJson(getIntent().getStringExtra("ItemModified"), type);
            Integer position = new Gson().fromJson(getIntent().getStringExtra("positionEditedItem"), typeInt);
            for (Medicine medicine :
                    medicines) {
                mMedicationList.set(Integer.valueOf(position), medicine);
            }
            storeToSharedPreferences();
        }
    }

    private void getNewItems(){
        if(getIntent().hasExtra("NewItem")){
            Type type = new TypeToken<List<Medicine>>(){}.getType();
            List<Medicine> medicines = new Gson().fromJson(getIntent().getStringExtra("NewItem"), type);

            for (Medicine medicine :
                    medicines) {
                mMedicationList.add(medicine);
            }
            storeToSharedPreferences();
        }
    }

    private void setAlarm(Medicine medicine){
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, 0);

        switch (medicine.getFrequence()){
            case "Jounalier" :
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Date.parse(medicine.getHourItem().getHourValue()), AlarmManager.INTERVAL_DAY, pendingIntent);
                break;
            case "Hebdomadaire" :
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Date.parse(medicine.getHourItem().getHourValue()), AlarmManager.INTERVAL_DAY * 7, pendingIntent);
                break;
            case "Mensuel" :
                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, Date.parse(medicine.getHourItem().getHourValue()), AlarmManager.INTERVAL_DAY * 30, pendingIntent);
                break;
        }
    }

    public void notifyAlarm(String name, String pathology, String moment, String quantity){
        String title = name + " - " + pathology;
        String content = quantity + " - " + moment;
        NotificationHelper notificationHelper = new NotificationHelper(getApplicationContext());
        NotificationCompat.Builder nb = notificationHelper.getChannelNotificationForALert(title, content);
        notificationHelper.getManager().notify(1, nb.build());

    }

    public void cancelAlarm(){
        if(mMedicationList.isEmpty()) {
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(getApplicationContext(), AlertReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, 0);

            alarmManager.cancel(pendingIntent);
        }
    }


}
