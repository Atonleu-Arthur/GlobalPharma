package com.example.globalpharma.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.globalpharma.Model.Medication;
import com.example.globalpharma.Model.MedicationForm;
import com.example.globalpharma.R;
import com.example.globalpharma.controller.MedicationFormAdapter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MedicationFormsActivity extends AppCompatActivity  {

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

        mMedicationForms.add(new MedicationForm("Comprimé", R.mipmap.ic_camera_foreground));
        mMedicationForms.add(new MedicationForm("Gellule", R.mipmap.ic_camera_foreground));
        mMedicationForms.add(new MedicationForm("Comprimé", R.mipmap.ic_camera_foreground));
        mMedicationForms.add(new MedicationForm("Comprimé", R.mipmap.ic_camera_foreground));
        mMedicationForms.add(new MedicationForm("Comprimé", R.mipmap.ic_camera_foreground));
        mMedicationForms.add(new MedicationForm("Comprimé", R.mipmap.ic_camera_foreground));
        mMedicationForms.add(new MedicationForm("Comprimé", R.mipmap.ic_camera_foreground));
        mMedicationForms.add(new MedicationForm("Comprimé", R.mipmap.ic_camera_foreground));
        mMedicationForms.add(new MedicationForm("Comprimé", R.mipmap.ic_camera_foreground));
        mMedicationForms.add(new MedicationForm("Comprimé", R.mipmap.ic_camera_foreground));
        mMedicationForms.add(new MedicationForm("Comprimé", R.mipmap.ic_camera_foreground));
        mMedicationForms.add(new MedicationForm("Comprimé", R.mipmap.ic_camera_foreground));
        mMedicationForms.add(new MedicationForm("Comprimé", R.mipmap.ic_camera_foreground));
        mMedicationForms.add(new MedicationForm("Comprimé", R.mipmap.ic_camera_foreground));
        mMedicationForms.add(new MedicationForm("Comprimé", R.mipmap.ic_camera_foreground));
        mMedicationForms.add(new MedicationForm("Comprimé", R.mipmap.ic_camera_foreground));
        mMedicationForms.add(new MedicationForm("Comprimé", R.mipmap.ic_camera_foreground));
        mMedicationForms.add(new MedicationForm("Comprimé", R.mipmap.ic_camera_foreground));
        mMedicationForms.add(new MedicationForm("Comprimé", R.mipmap.ic_camera_foreground));
        mMedicationForms.add(new MedicationForm("Comprimé", R.mipmap.ic_camera_foreground));
        mMedicationForms.add(new MedicationForm("Comprimé", R.mipmap.ic_camera_foreground));

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
               intent.putExtra("SelectedMedicationFormInfo", serializeMedicationForm(medicationForm));
                MedicationFormsActivity.this.startActivity(intent);
                finish();
            }
        });

    }
    public String serializeMedicationForm(MedicationForm object){
        Gson gson = new Gson();
        return gson.toJson(object);
    }


}
