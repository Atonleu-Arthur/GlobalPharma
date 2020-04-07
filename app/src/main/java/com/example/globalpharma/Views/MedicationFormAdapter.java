package com.example.globalpharma.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globalpharma.Model.MedicationForm;
import com.example.globalpharma.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class MedicationFormAdapter extends RecyclerView.Adapter<MedicationFormAdapter.MedicationFormViewHolder> {

    List<MedicationForm> mMedicationForms;
    Context mContext;
    private TextInputEditText mTextForm;

    public MedicationFormAdapter(List<MedicationForm> medicationForms, Context context) {
        mMedicationForms = medicationForms;
        mContext = context;
    }

    @NonNull
    @Override
    public MedicationFormAdapter.MedicationFormViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.form_item, viewGroup, false);
        return new MedicationFormViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationFormAdapter.MedicationFormViewHolder medicationFormViewHolder, int i) {
        medicationFormViewHolder.mImageViewForm.setImageResource(mMedicationForms.get(i).getFormImage());
        medicationFormViewHolder.mTextViewForm.setText(mMedicationForms.get(i).getFormName());
    }

    @Override
    public int getItemCount() {
        return mMedicationForms.size();
    }

    public class MedicationFormViewHolder extends RecyclerView.ViewHolder {
        TextView mTextViewForm;
        ImageView mImageViewForm;

        public MedicationFormViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageViewForm = itemView.findViewById(R.id.img_form_medication);
            mTextViewForm = itemView.findViewById(R.id.text_form_medication);
            mTextForm = itemView.findViewById(R.id.text_add_medication_form);
        }
    }


}
