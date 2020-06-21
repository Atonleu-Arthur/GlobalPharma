package com.example.globalpharma.Views;

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
    private OnItemClickListener mListener;
    private Boolean isDeletion = false;


    public interface OnItemClickListener{
        void onDeleteClick(int position);
        void onEditClick(int position);
    }

    public void setOnItemClickListener(MedicationAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

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
        holder.mMedicationQuantity.setText(mMedications.get(position).getTotalQuantity());
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
        ImageView mImageDelete;
        ImageView mImageEdit;

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
            mImageDelete = itemView.findViewById(R.id.img_delete);
            mImageEdit = itemView.findViewById(R.id.img_edit);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mImageDelete.isSelected()){
                        mImageDelete.setOnClickListener(new ImageView.OnClickListener() {
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
                    else if(mImageEdit.isSelected()){
                        mImageEdit.setOnClickListener(new ImageView.OnClickListener() {
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
