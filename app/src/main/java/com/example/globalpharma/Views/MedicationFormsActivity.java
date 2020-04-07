package com.example.globalpharma.Views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.globalpharma.Model.MedicationForm;
import com.example.globalpharma.R;
import com.google.android.material.textfield.TextInputEditText;

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
        mMedicationForms.add(new MedicationForm("Comprimé", R.mipmap.ic_camera_foreground));

        mMedicationFormAdapter = new MedicationFormAdapter(mMedicationForms, this);
        mRecyclerView.setAdapter(mMedicationFormAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

}
