package com.example.globalpharma.Model;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.globalpharma.R;

public class Medication {
    private String mMedicationName;
    private String mTreatedPart;
    private String mMedicationForm;
    private String mMedicationQuantityText;
    private String mMedicationHour;
    @Nullable private String mMedicationPrecision;
    private String mMissingTime;
    private int mMedicationImage;

    public Medication(String medicationName, String treatedPart, String medicationForm,
                      String medicationQuantity, String medicationHour,
                      @Nullable String medicationPrecision, String missingTime,
                      int medicationImage) {
        mMedicationName = medicationName;
        mTreatedPart = treatedPart;
        mMedicationForm = medicationForm;
        mMedicationHour = medicationHour;
        mMedicationPrecision = medicationPrecision;
        mMissingTime = missingTime;
        mMedicationImage = medicationImage;
        mMedicationQuantityText = medicationQuantity;
    }

    public String getMedicationName() {
        return mMedicationName;
    }

    public void setMedicationName(String medicationName) {
        mMedicationName = medicationName;
    }

    public String getTreatedPart() {
        return mTreatedPart;
    }

    public void setTreatedPart(String treatedPart) {
        mTreatedPart = treatedPart;
    }

    public String getMedicationForm() {
        return mMedicationForm;
    }

    public void setMedicationForm(String medicationForm) {
        mMedicationForm = medicationForm;
    }

    public String getMedicationQuantity() {
        return mMedicationQuantityText;
    }

    public void setMedicationQuantity(String medicationQuantityText) {
        mMedicationQuantityText = medicationQuantityText;
    }

    public String getMedicationHour() {
        return mMedicationHour;
    }

    public void setMedicationHour(String medicationHour) {
        mMedicationHour = medicationHour;
    }

    @Nullable
    public String getMedicationPrecision() {
        return mMedicationPrecision;
    }

    public void setMedicationPrecision(@Nullable String medicationPrecision) {
        mMedicationPrecision = medicationPrecision;
    }

    public String getMissingTime() {
        return mMissingTime;
    }

    public void setMissingTime(String missingTime) {
        mMissingTime = missingTime;
    }

    public int getMedicationImage() {
        return mMedicationImage;
    }

    public void setMedicationImage(int medicationImage) {
        mMedicationImage = medicationImage;
    }
}
