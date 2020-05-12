package com.example.globalpharma.Views;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
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

public class MedicationFormAdapter extends RecyclerView.Adapter<MedicationFormAdapter.MedicationFormViewHolder> implements Parcelable {

    List<MedicationForm> mMedicationForms;
    Context mContext;
    private TextInputEditText mTextForm;
    private OnItemClickListener mOnItemClickListener;

    protected MedicationFormAdapter(Parcel in) {
    }

    public static final Creator<MedicationFormAdapter> CREATOR = new Creator<MedicationFormAdapter>() {
        @Override
        public MedicationFormAdapter createFromParcel(Parcel in) {
            return new MedicationFormAdapter(in);
        }

        @Override
        public MedicationFormAdapter[] newArray(int size) {
            return new MedicationFormAdapter[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }


    public interface OnItemClickListener{
        public void onItemClicked(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener = listener;
    }

    public MedicationFormAdapter(List<MedicationForm> medicationForms, Context context) {
        mMedicationForms = medicationForms;
        mContext = context;
    }

    @NonNull
    @Override
    public MedicationFormAdapter.MedicationFormViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.form_item, viewGroup, false);
        return new MedicationFormViewHolder(layout, mOnItemClickListener);
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

        public MedicationFormViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            mImageViewForm = itemView.findViewById(R.id.img_form_medication);
            mTextViewForm = itemView.findViewById(R.id.text_form_medication);
            mTextForm = itemView.findViewById(R.id.text_add_medication_form);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onItemClicked(position);
                        }
                    }
                }
            });
        }
    }


}
