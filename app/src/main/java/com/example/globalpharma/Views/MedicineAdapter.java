package com.example.globalpharma.Views;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.globalpharma.Model.Medicine;
import com.example.globalpharma.R;

import java.util.List;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineViewHolder> {
    private List<Medicine> mMedicines;
    private MedicineAdapter.OnItemClickListener mListener;
    private Boolean isDeletion = false;


    public interface OnItemClickListener{
        void onDeleteClick(int position);
        void onEditClick(int position);
    }

    public void setOnItemClickListener(MedicineAdapter.OnItemClickListener listener) {
        mListener = listener;
    }
    
    public MedicineAdapter(List<Medicine> medicines){
        mMedicines = medicines;
    }
    
    @NonNull
    @Override
    public MedicineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_item, parent, false);
        return new MedicineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicineViewHolder holder, int position) {
        holder.mDateOfDay.setText(mMedicines.get(position).getDate());
        holder.mForm.setText(mMedicines.get(position).getForm());
        holder.mName.setText(mMedicines.get(position).getName());
        holder.mPathology.setText(mMedicines.get(position).getPathology());
        holder.mQuantityPerDay.setText(mMedicines.get(position).getQuantityPerDay() + " ");
        holder.mHour.setText(mMedicines.get(position).getHourItem().getHourValue());
        holder.mMoment.setText(mMedicines.get(position).getMoment());
        holder.mMissingTime.setText("Il vous reste " + mMedicines.get(position).getMissingTime() + " jours");
        holder.mMedicationImage.setImageBitmap(mMedicines.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return mMedicines.size();
    }

    public class MedicineViewHolder extends RecyclerView.ViewHolder {
        private TextView mName;
        private TextView mForm;
        private TextView mPathology;
        private TextView mQuantityPerDay;
        private TextView mMoment;
        private TextView mHour;
        private TextView mMissingTime;
        private TextView mDateOfDay;
        private ImageView mEdit;
        private ImageView mDelete;
        private ImageView mMedicationImage;

        public MedicineViewHolder(View itemView) {
            super(itemView);
            mName = itemView.findViewById(R.id.medication_name);
            mPathology = itemView.findViewById(R.id.part_of_body_treated);
            mForm = itemView.findViewById(R.id.medication_form);
            mQuantityPerDay = itemView.findViewById(R.id.quantity);
            mMoment = itemView.findViewById(R.id.precision);
            mDateOfDay = itemView.findViewById(R.id.date_value);
            mMedicationImage = itemView.findViewById(R.id.medication_image);
            mDelete = itemView.findViewById(R.id.img_delete);
            mEdit = itemView.findViewById(R.id.img_edit);
            mHour = itemView.findViewById(R.id.hour_alarm);
            mMissingTime = itemView.findViewById(R.id.missing_time);

            mDelete.setOnClickListener(new ImageView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isDeletion = true;
                    if(mListener != null ){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION && isDeletion == true) {
                            mListener.onDeleteClick(position);
                        }
                    }
                }
            });

            mEdit.setOnClickListener(new ImageView.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isDeletion = false;
                    if(mListener != null ){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION && isDeletion == false) {
                            mListener.onEditClick(position);
                        }
                    }
                }
            });
        }
    }
}
