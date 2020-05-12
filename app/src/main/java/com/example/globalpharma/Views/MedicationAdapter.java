package com.example.globalpharma.Views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globalpharma.Model.Medication;
import com.example.globalpharma.R;

import java.util.List;

public class MedicationAdapter extends RecyclerView.Adapter<MedicationAdapter.MedicationViewHolder> {
    List<Medication> mMedications;

    public MedicationAdapter(List<Medication> medications) {
        mMedications = medications;
    }

    @NonNull
    @Override
    public MedicationAdapter.MedicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout;
        layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.medication_item, parent, false);
        return new MedicationViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationAdapter.MedicationViewHolder holder, int position) {
        holder.mMedicationForm.setText(mMedications.get(position).getMedicationForm());
        holder.mMedicationName.setText(mMedications.get(position).getMedicationName());
        holder.mTreatedPart.setText(mMedications.get(position).getTreatedPart());
        holder.mMedicationQuantity.setText(mMedications.get(position).getMedicationQuantity());
        holder.mMedicationHour.setText(mMedications.get(position).getMedicationHour());
        holder.mMedicationPrecision.setText(mMedications.get(position).getMedicationPrecision());
        holder.mMissingTime.setText(mMedications.get(position).getMissingTime());
        holder.mMedicationImage.setImageResource(mMedications.get(position).getMedicationImage());
    }

    @Override
    public int getItemCount() {
        return mMedications.isEmpty() ? 0 : mMedications.size();
    }

    public class MedicationViewHolder extends RecyclerView.ViewHolder {
        TextView mMedicationName;
        TextView mTreatedPart;
        TextView mMedicationForm;
        TextView mMedicationQuantity;
        TextView mMedicationHour;
        @Nullable TextView mMedicationPrecision;
        TextView mMissingTime;
        ImageView mMedicationImage;

        public MedicationViewHolder(@NonNull View itemView) {
            super(itemView);
            mMedicationName = itemView.findViewById(R.id.medication_name);
            mTreatedPart = itemView.findViewById(R.id.part_of_body_treated);
            mMedicationForm = itemView.findViewById(R.id.medication_form);
            mMedicationQuantity = itemView.findViewById(R.id.quantity);
            mMedicationHour = itemView.findViewById(R.id.hour_alarm);
            mMedicationPrecision = itemView.findViewById(R.id.precision);
            mMissingTime = itemView.findViewById(R.id.missing_time);
            mMedicationImage = itemView.findViewById(R.id.medication_image);
        }
    }
}
