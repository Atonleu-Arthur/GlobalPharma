package com.example.globalpharma.Model;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globalpharma.R;
import com.example.globalpharma.Views.MedicationAdapter;

import java.util.ArrayList;
import java.util.List;

public class MedicationPerDayAdapter extends RecyclerView.Adapter<MedicationPerDayAdapter.MedicationPerDayViewHolder> {
    private List<MedicationPerDay> mMedicationPerDayList;
    private Activity mActivity;

    public MedicationPerDayAdapter(List<MedicationPerDay> medicationPerDayList, Activity activity) {
        mMedicationPerDayList = medicationPerDayList;
        mActivity = activity;
    }

    @NonNull
    @Override
    public MedicationPerDayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.medication_per_day_item, parent, false);
        return new MedicationPerDayViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationPerDayViewHolder holder, int position) {
        holder.dateValue.setText(mMedicationPerDayList.get(position).getDate());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity);

        MedicationAdapter medicationAdapter = new MedicationAdapter(mMedicationPerDayList.get(position).getMedicationsOfDay());
        holder.mRecyclerView.setLayoutManager(linearLayoutManager);
        holder.mRecyclerView.setAdapter(medicationAdapter);
    }

    @Override
    public int getItemCount() {
        return mMedicationPerDayList.size();
    }

    public class MedicationPerDayViewHolder extends RecyclerView.ViewHolder {
        TextView dateValue;
        RecyclerView mRecyclerView;

        public MedicationPerDayViewHolder(@NonNull View itemView) {
            super(itemView);
            dateValue = itemView.findViewById(R.id.date_value);
            mRecyclerView = itemView.findViewById(R.id.rv_medications_per_day);
        }
    }
}
