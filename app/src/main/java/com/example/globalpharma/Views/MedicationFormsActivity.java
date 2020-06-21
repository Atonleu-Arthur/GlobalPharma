package com.example.globalpharma.Views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globalpharma.Model.MedicationForm;
import com.example.globalpharma.R;
import com.example.globalpharma.controller.MedicationFormAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MedicationFormsActivity extends AppCompatActivity  {

    private static final String SP_NAME_FORM = "MedicationForm";
    private MedicationFormAdapter mMedicationFormAdapter;
    private RecyclerView mRecyclerView;
    private List<MedicationForm> mMedicationForms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_forms);

        getSupportActionBar().hide();

        mRecyclerView = findViewById(R.id.rv_medication_form);

        mMedicationForms = new ArrayList<>();

        mMedicationForms.add(new MedicationForm("Comprimé", R.mipmap.ic_pill_foreground));
        mMedicationForms.add(new MedicationForm("Comprimé effervescent", R.mipmap.ic_effervescent_foreground));
        mMedicationForms.add(new MedicationForm("Gellule", R.mipmap.ic_capsule_foreground));
        mMedicationForms.add(new MedicationForm("Pastille", R.mipmap.ic_pastille_foreground));
        mMedicationForms.add(new MedicationForm("Sirop / Solution Buvable", R.mipmap.ic_spoon_foreground));
        mMedicationForms.add(new MedicationForm("Ampoule", R.mipmap.ic_ampoule_foreground));
        mMedicationForms.add(new MedicationForm("Goutte", R.mipmap.ic_goutte_foreground));
        mMedicationForms.add(new MedicationForm("Sachet", R.mipmap.ic_sachet_foreground));
        mMedicationForms.add(new MedicationForm("Pommade", R.mipmap.ic_ointment_foreground));
        mMedicationForms.add(new MedicationForm("Injection", R.mipmap.ic_syringe_foreground));
        mMedicationForms.add(new MedicationForm("Collyre", R.mipmap.ic_eye_drop_foreground));


        mMedicationFormAdapter = new MedicationFormAdapter(mMedicationForms, this);
        mRecyclerView.setAdapter(mMedicationFormAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mMedicationFormAdapter.setOnItemClickListener(new MedicationFormAdapter.OnItemClickListener() {
            @Override
            public void onItemClicked(int position) {
               String form = mMedicationForms.get(position).getFormName();
               int imageResId = mMedicationForms.get(position).getFormImage();
               MedicationForm medicationForm = new MedicationForm(form, imageResId);
               Intent intent = new Intent(MedicationFormsActivity.this, NewMedicationActivity.class);
               intent.putExtra(SP_NAME_FORM, serializeMedicationForm(medicationForm));
               startActivity(intent);
               finish();
            }
        });

    }

    public String serializeMedicationForm(MedicationForm object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    private void storeFormToSharedPreferences(MedicationForm medicationForm){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(medicationForm);
        editor.putString(SP_NAME_FORM, json);
        editor.apply();
    }

}
