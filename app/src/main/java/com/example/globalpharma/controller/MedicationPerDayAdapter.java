package com.example.globalpharma.controller;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globalpharma.Model.Medication;
import com.example.globalpharma.Model.MedicationPerDay;
import com.example.globalpharma.R;
import com.example.globalpharma.Views.Accueil;
import com.example.globalpharma.Views.MedicationAdapter;
import com.google.android.gms.flags.impl.SharedPreferencesFactory;
import com.google.gson.Gson;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.globalpharma.Views.Accueil.SP_NAME_STRING;

public class MedicationPerDayAdapter extends RecyclerView.Adapter<MedicationPerDayAdapter.MedicationPerDayViewHolder> {
    private List<MedicationPerDay> mMedicationPerDayList;
    private Activity mActivity;
    private MedicationAdapter.OnItemClickListener mListener;
    private Boolean isDeletion = false;


    public interface OnItemClickListener{
        void onDeleteClick(int position);
        void onEditClick(int position);
    }

    public void setOnItemClickListener(MedicationAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

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
        MedicationAdapter mMedicationAdapter;
        List<Medication> mMedications;
        ImageView mImageViewDelete;
        ImageView mImageViewEdit;

        public MedicationPerDayViewHolder(@NonNull View itemView) {
            super(itemView);
            dateValue = itemView.findViewById(R.id.date_value);
            mRecyclerView = itemView.findViewById(R.id.rv_medications_per_day);
            mImageViewEdit = itemView.findViewById(R.id.img_edit);
            mImageViewDelete = itemView.findViewById(R.id.img_delete);
            mMedications = mMedicationPerDayList.get(0).getMedicationsOfDay();
            mMedicationAdapter = new MedicationAdapter(mMedications);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mImageViewDelete.isSelected()){
                        mImageViewDelete.setOnClickListener(new ImageView.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                isDeletion = true;
                                if(mListener != null ){
                                    int position = getAdapterPosition();
                                    if (position != RecyclerView.NO_POSITION) {
                                        mListener.onDeleteClick(position);
                                    }
                                }
                            }
                        });
                    }
                    else if(mImageViewEdit.isSelected()){
                        mImageViewEdit.setOnClickListener(new ImageView.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                isDeletion = false;
                                if(mListener != null){
                                    int position = getAdapterPosition();
                                    if (position != RecyclerView.NO_POSITION && isDeletion == false) {
                                        mListener.onEditClick(position);
                                    }
                                }
                            }
                        });
                    }
                }
            });
        }


    }
}
